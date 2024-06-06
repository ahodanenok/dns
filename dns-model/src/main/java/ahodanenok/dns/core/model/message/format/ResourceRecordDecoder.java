package ahodanenok.dns.core.model.message.format;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.model.ResourceRecord;

public interface ResourceRecordDecoder<R extends ResourceRecord, S extends MessageDecoderState> {

    R createInitialRecord();

    String getRecordType();

    void decodeRData(ByteBuffer buf, S state, R target);
}
