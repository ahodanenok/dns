package ahodanenok.dns.core.masterfile;

import java.io.InputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackInputStream;

public class MasterFileDataReader {

    private final PushbackInputStream in;
    private String lineSeparator;

    public MasterFileDataReader(InputStream in) {
        this.lineSeparator = System.lineSeparator();
        this.in = new PushbackInputStream(in, this.lineSeparator.length());
    }

    public String readString() throws IOException {
        skipBlanks();

        int ch;
        StringBuilder buf = new StringBuilder();
        while ((ch = in.read()) != -1) {
            if (!CharacterUtils.isAscii(ch)) {
                throw new BadEncodingException(String.format(
                    "Only ASCII characters are supported, got '0x%x'", ch));
            }

            if (CharacterUtils.isBlank(ch) || isLineSeparatorAhead(ch)) {
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

    private void skipBlanks() throws IOException {
        int ch;
        while ((ch = in.read()) != -1) {
            if (!CharacterUtils.isBlank(ch) || isLineSeparatorAhead(ch)) {
                break;
            }
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
