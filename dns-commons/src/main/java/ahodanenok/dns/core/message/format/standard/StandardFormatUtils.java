package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.DomainName;

public class StandardFormatUtils {

    static final int BYTE_MASK = 0xFF;

    public static int decodeUInt16(ByteBuffer buf) {
        return ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK);
    }

    public static void encodeUInt16(int n, ByteBuffer buf) {
        buf.put((byte) (n >> 8));
        buf.put((byte) n);
    }

    public static void encodeUInt32(int n, ByteBuffer buf) {
        buf.put((byte) (n >> 24));
        buf.put((byte) (n >> 16));
        buf.put((byte) (n >> 8));
        buf.put((byte) n);
    }

    public static int getEncodedDomainNameLength(DomainName name) {
        int length = 0;
        for (String label : name.labels()) {
            length += 1;
            length += label.length();
        }

        return length;
    }

    public static void encodeDomainName(DomainName name, ByteBuffer buf) {
        for (String label : name.labels()) {
            buf.put((byte) label.length());
            if (label.length() > 0) {
                for (int i = 0, n = label.length(); i < n; i++) {
                    buf.put((byte) label.charAt(i));
                }
            }
        }
    }
}
