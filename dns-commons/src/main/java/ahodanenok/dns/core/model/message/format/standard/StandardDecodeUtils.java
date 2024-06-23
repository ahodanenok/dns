package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;

public class StandardDecodeUtils {

    static final int BYTE_MASK = 0xFF;

    public static int decodeUInt16(ByteBuffer buf) {
        return ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK);
    }
}
