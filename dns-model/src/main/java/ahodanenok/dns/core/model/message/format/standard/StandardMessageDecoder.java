package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import ahodanenok.dns.core.model.message.format.MessageDecoder;

public final class StandardMessageDecoder implements MessageDecoder<StandardMessageDecoderState> {

    private final Map<String, StandardResourceRecordDecoder> recordDecoders = new HashMap<>();

    public void addRecordDecoder(StandardResourceRecordDecoder recordDecoder) {
        // todo: already registered?
        recordDecoders.put(recordDecoder.getRecordType(), recordDecoder);
    }

    @Override
    public StandardMessageDecoderState decode(ByteBuffer buf, boolean last) {
        return null;
    }

    @Override
    public void decode(ByteBuffer buf, StandardMessageDecoderState state, boolean last) {

    }
}
