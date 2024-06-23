package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

public class StandardFormatUtils {

    static final int BYTE_MASK = 0xFF;

    public static int decodeUInt16(ByteBuffer buf) {
        return ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK);
    }

    public static void encodeUInt16(int n, ByteBuffer buf) {
        buf.put((byte) (n >> 8));
        buf.put((byte) n);
    }
}
