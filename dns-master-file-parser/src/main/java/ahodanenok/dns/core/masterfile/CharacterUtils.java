package ahodanenok.dns.core.masterfile;

public final class CharacterUtils {

    private static final int ASCII_MASK = ~(0x7F);

    public static boolean isBlank(int ch) {
        return ch == ' ' || ch == '\t';
    }

    public static boolean isAscii(int ch) {
        return (ch & ASCII_MASK) == 0;
    }
}
