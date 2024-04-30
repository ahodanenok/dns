package ahodanenok.dns.core.masterfile;

import java.io.InputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackInputStream;

public class MasterFileDataReader {

    private static final char COMMENT_CHAR = ';';
    private static final char ESCAPE_SEQUENCE_CHAR = '\\';

    private final PushbackInputStream in;
    private String lineSeparator;

    public MasterFileDataReader(InputStream in) {
        this.lineSeparator = System.lineSeparator();
        this.in = new PushbackInputStream(in, this.lineSeparator.length());
    }

    public String readString() throws IOException {
        skipNonReadable();

        int ch;
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

        if (buf.length() == 0 && ch == -1) {
            throw new EOFException();
        } else if (buf.length() == 0) {
            throw new NoValueReadException("No string to read");
        }

        if (ch != -1) {
            in.unread(ch);
        }

        return buf.toString();
    }

    private void readEscapeBodyTo(StringBuilder buf) throws IOException {
        int ch = in.read();
        if (ch == -1) {
            throw new EOFException();
        }

        if (!CharacterUtils.isDigit(ch)) {
            if (isLineSeparatorAhead(ch)) {
                if (lineSeparator.length() == 2) {
                    in.read();
                }

                if (buf != null) {
                    buf.append(lineSeparator);
                }

                return;
            }

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
        int ch;
        while ((ch = in.read()) != -1) {
            if (ch == ESCAPE_SEQUENCE_CHAR) {
                readEscapeBodyTo(null);
                continue;
            }

            if (!CharacterUtils.isBlank(ch) || isLineSeparatorAhead(ch)) {
                break;
            }
        }

        if (ch != -1) {
            in.unread(ch);
        }
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
}
