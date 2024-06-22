package ahodanenok.dns.core.model.message.format;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.message.QRecordType;

public interface ResourceRecordDecoder<R extends ResourceRecord, S extends MessageDecoderState> {

    R createInitialRecord();

    QRecordType getRecordType();

    void decodeRecordData(ByteBuffer buf, int length, S state, R target);
}
