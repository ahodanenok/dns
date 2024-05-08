package ahodanenok.dns.core.masterfile;

import java.io.Closeable;
import java.io.InputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackInputStream;

// todo: implement reading quoted strings
// todo: implement \ddd escapes
// todo: add support for (...)
public class MasterFileDataReader implements Closeable {

    public static final int EOF_MARKER = -1;
    public static final int EOL_MARKER = -2;

    private static final char COMMENT_CHAR = ';';
    private static final char ESCAPE_SEQUENCE_CHAR = '\\';

    private final PushbackInputStream in;
    private String lineSeparator;
    private boolean stl; // start of line
    private String stlBlank;

    public MasterFileDataReader(InputStream in) {
        this.lineSeparator = System.lineSeparator();
        this.in = new PushbackInputStream(in, this.lineSeparator.length());
        this.stl = true;
    }

    public void advance() throws IOException {
        if (stlBlank != null) {
            return;
        }

        int ch = -1;
        while (true) {
            skipNonReadable();
            if (stlBlank != null) {
                break;
            }

            ch = in.read();
            if (!isLineSeparatorAhead(ch)) {
                if (ch != -1) {
                    in.unread(ch);
                }

                break;
            }

            if (lineSeparator.length() == 2) {
                in.read();
            }

            stl = true;
        }
    }

    public int peek() throws IOException {
        skipNonReadable();
        if (stlBlank != null) {
            return stlBlank.charAt(0);
        }

        int ch = in.read();
        if (ch == -1) {
            return EOF_MARKER;
        }

        if (isLineSeparatorAhead(ch)) {
            in.unread(ch);
            return EOL_MARKER;
        }

        in.unread(ch);
        return ch;
    }

    public String readString() throws IOException {
        skipNonReadable();
        if (stlBlank != null) {
            String str = stlBlank;
            stlBlank = null;
            return str;
        }

        int ch;
        // todo: share a buffer between calls?
        StringBuilder buf = new StringBuilder();
        while ((ch = in.read()) != -1) {
            if (!CharacterUtils.isAscii(ch)) {
                throw new BadEncodingException(String.format(
                    "Only ASCII characters are supported, got '0x%x'", ch));
            }

            if (ch == '\\') {
                readEscapeBodyTo(buf);
                continue;
            }

            if (CharacterUtils.isBlank(ch) || ch == COMMENT_CHAR || isLineSeparatorAhead(ch)) {
                break;
            }

            buf.append((char) ch);
        }

        if (ch != -1) {
            in.unread(ch);
        }

        if (buf.length() == 0 && ch == -1) {
            throw new EOFException();
        } else if (buf.length() == 0) {
            throw new NoValueReadException("No string to read");
        }

        return buf.toString();
    }

    private void readEscapeBodyTo(StringBuilder buf) throws IOException {
        int ch = in.read();
        if (ch == -1) {
            throw new EOFException();
        }

        if (!CharacterUtils.isDigit(ch)) {
            if (buf != null) {
                buf.append((char) ch);
            }

            return;
        }

        throw new UnsupportedEscapeSequenceException(String.format("%c%c", ESCAPE_SEQUENCE_CHAR, ch));
    }

    private void skipNonReadable() throws IOException {
        skipBlanks();
        skipComment();
    }

    private void skipBlanks() throws IOException {
        if (stlBlank != null) {
            return;
        }

        int ch;
        int blank = -1;
        while ((ch = in.read()) != -1) {
            if (ch == ESCAPE_SEQUENCE_CHAR) {
                break;
            }

            if (isLineSeparatorAhead(ch)) {
                break;
            }

            if (!CharacterUtils.isBlank(ch)) {
                if (blank != -1) {
                    // if there are only two types of blank characters, use constants?
                    stlBlank = "" + (char) blank;
                }

                break;
            } else if (stl && blank == -1) {
                blank = ch;
            }
        }

        if (ch != -1) {
            in.unread(ch);
        }

        stl = false;
    }

    private void skipComment() throws IOException {
        int ch = in.read();
        if (ch != COMMENT_CHAR) {
            if (ch != -1) {
                in.unread(ch);
            }

            return;
        }

        while (ch != -1) {
            if (ch == ESCAPE_SEQUENCE_CHAR) {
                readEscapeBodyTo(null);
            } else if (isLineSeparatorAhead(ch)) {
                break;
            }

            ch = in.read();
        }

        if (ch != -1) {
            in.unread(ch);
        }

        stlBlank = null;
        stl = false;
    }

    private boolean isLineSeparatorAhead(int ch) throws IOException {
        if (lineSeparator.length() == 1 && lineSeparator.charAt(0) == (char) ch) {
            return true;
        }

        if (lineSeparator.length() == 2) {
            if (lineSeparator.charAt(0) != ch) {
                return false;
            }

            int nextCh = in.read();
            if (nextCh == -1) {
                return false;
            }
            in.unread(nextCh);

            return lineSeparator.charAt(1) == (char) nextCh;
        }

        throw new IllegalStateException(String.format(
            "Hmmmm, line separator is more than two characters '%s'",
            java.util.Arrays.toString(lineSeparator.getBytes("UTF-8"))));
    }

    public void close() throws IOException {
        in.close();
    }
}
