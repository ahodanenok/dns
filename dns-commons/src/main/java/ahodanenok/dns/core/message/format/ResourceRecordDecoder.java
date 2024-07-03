package ahodanenok.dns.core.message.format;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;

public interface ResourceRecordDecoder<R extends ResourceRecord, S extends MessageDecoderState> {

    R createInitialRecord();

    RecordType getRecordType();

    void decodeRecordData(ByteBuffer buf, int length, S state, R target);
}
