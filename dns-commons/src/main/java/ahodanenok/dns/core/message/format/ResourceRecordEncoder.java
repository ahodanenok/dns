package ahodanenok.dns.core.message.format;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;

public interface ResourceRecordEncoder<R extends ResourceRecord> {

    RecordType getRecordType();

    int getExpectedDataLength(R record);

    int encodeRecordData(R record, ByteBuffer out);
}
