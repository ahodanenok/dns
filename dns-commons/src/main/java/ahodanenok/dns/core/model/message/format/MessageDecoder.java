package ahodanenok.dns.core.model.message.format;

import java.nio.ByteBuffer;

public interface MessageDecoder<S extends MessageDecoderState> {

    S decode(ByteBuffer buf, boolean last);

    void decode(ByteBuffer buf, S state, boolean last);
}
