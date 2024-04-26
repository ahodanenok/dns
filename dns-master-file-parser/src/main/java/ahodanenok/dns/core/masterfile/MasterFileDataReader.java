package ahodanenok.dns.core.masterfile;

import java.io.InputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PushbackInputStream;

public class MasterFileDataReader {

    private final PushbackInputStream in;

    public MasterFileDataReader(InputStream in) {
        this.in = new PushbackInputStream(in);
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

            if (CharacterUtils.isBlank(ch)) {
                break;
            }

            buf.append((char) ch);
        }

        if (buf.length() == 0 && ch == -1) {
            throw new EOFException();
        } else if (buf.length() == 0) {
            throw new IOException("Couldn't read a string for an unknown reason, possibly a bug");
        }

        if (ch != -1) {
            in.unread(ch);
        }

        return buf.toString();
    }

    private void skipBlanks() throws IOException {
        int ch;
        while ((ch = in.read()) != -1 && CharacterUtils.isBlank(ch));
        if (ch != -1) {
            in.unread(ch);
        }
    }
}
