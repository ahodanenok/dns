package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.message.Message;
import ahodanenok.dns.core.message.MessageHeader;
import ahodanenok.dns.core.message.format.MessageEncoder;

public final class StandardMessageEncoder implements MessageEncoder {

    // todo: length limit
    // todo: truncate

    public ByteBuffer encode(Message message) {
        ByteBuffer buf = ByteBuffer.allocate(512);

        MessageHeader header = message.getHeader();
        StandardFormatUtils.encodeUInt16(header.getId(), buf);

        int b = !header.isQuery() ? 0x80 : 0x0;
        b |= header.getOperation().getCode() << 3;
        if (header.isAuthoritative()) {
            b |= 0x4;
        }
        if (header.isTruncated()) {
            b |= 0x2;
        }
        if (header.isRecursionDesired()) {
            b |= 0x1;
        }
        buf.put((byte) b);

        b = header.isRecursionAvailable() ? 0x80 : 0x0;
        // Z=000
        b |= header.getResponseStatus().getCode();
        buf.put((byte) b);

        StandardFormatUtils.encodeUInt16(header.getQuestionCount(), buf);
        StandardFormatUtils.encodeUInt16(header.getAnswerCount(), buf);
        StandardFormatUtils.encodeUInt16(header.getAuthorityCount(), buf);
        StandardFormatUtils.encodeUInt16(header.getAdditionalCount(), buf);

        return buf;
    }
}
