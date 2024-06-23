package ahodanenok.dns.core.message.format;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.message.QRecordType;

public interface ResourceRecordDecoder<R extends ResourceRecord, S extends MessageDecoderState> {

    R createInitialRecord();

    QRecordType getRecordType();

    void decodeRecordData(ByteBuffer buf, int length, S state, R target);
}
