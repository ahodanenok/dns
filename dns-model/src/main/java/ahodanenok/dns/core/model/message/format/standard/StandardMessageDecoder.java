package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import ahodanenok.dns.core.model.message.DefaultMessageHeader;
import ahodanenok.dns.core.model.message.Operation;
import ahodanenok.dns.core.model.message.ResponseStatus;
import ahodanenok.dns.core.model.message.format.MessageDecoder;
import ahodanenok.dns.core.model.message.format.MessageFormatException;

public final class StandardMessageDecoder implements MessageDecoder<StandardMessageDecoderState> {

    private static final int BYTE_MASK = 0xFF;

    private final Map<String, StandardResourceRecordDecoder> recordDecoders = new HashMap<>();

    public void addRecordDecoder(StandardResourceRecordDecoder recordDecoder) {
        // todo: already registered?
        recordDecoders.put(recordDecoder.getRecordType(), recordDecoder);
    }

    // todo: implement incremental decoding

    @Override
    public StandardMessageDecoderState decode(ByteBuffer buf, boolean last) {
        // header size is 6x16=96bytes

        if (!last) {
            throw new UnsupportedOperationException("Incremental decoding is not implemented");
        }

        StandardMessageDecoderState state = new StandardMessageDecoderState();

        DefaultMessageHeader header = new DefaultMessageHeader(
            ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK));

        byte flags = buf.get();
        header.setQuery((flags & 0x80) == 0);
        Operation operation = Operation.fromCode((flags & 0x78) >> 3);
        if (operation == null) {
            throw new MessageFormatException("Unknown OPCODE: " + ((flags & 0x78) >> 3));
        }
        header.setOperation(operation);
        header.setAuthoritative((flags & 0x4) > 0);
        header.setTruncated((flags & 0x2) > 0);
        header.setRecursionDesired((flags & 0x1) > 0);

        flags = buf.get();
        header.setRecursionAvailable((flags & 0x80) > 0);
        if ((flags & 0x70) != 0) {
            throw new MessageFormatException("Field Z must be 0, got " + ((flags & 0x70) >> 4));
        }
        ResponseStatus responseStatus = ResponseStatus.fromCode(flags & 0xF);
        if (responseStatus == null) {
            throw new MessageFormatException("Unknown RCODE: " + (flags & 0xF));
        }
        header.setResponseStatus(responseStatus);

        header.setQuestionCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));
        header.setAnswerCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));
        header.setAuthorityCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));
        header.setAdditionalCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));

        state.setHeader(header);

        return state;
    }

    @Override
    public void decode(ByteBuffer buf, StandardMessageDecoderState state, boolean last) {
        throw new UnsupportedOperationException("Incremental decoding is not implemented");
    }
}
