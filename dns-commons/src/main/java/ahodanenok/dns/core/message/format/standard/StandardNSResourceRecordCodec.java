package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.NSResourceRecord;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.StandardRecordType;

public final class StandardNSResourceRecordCodec
        implements StandardResourceRecordDecoder<NSResourceRecord>, StandardResourceRecordEncoder<NSResourceRecord> {

    @Override
    public NSResourceRecord createInitialRecord() {
        return new NSResourceRecord();
    }

    @Override
    public RecordType getRecordType() {
        return StandardRecordType.NS;
    }

    @Override
    public void decodeRecordData(ByteBuffer buf, int length, StandardMessageDecoderState state, NSResourceRecord record) {

    }

    @Override
    public int getExpectedDataLength(NSResourceRecord record) {
        return StandardFormatUtils.getEncodedDomainNameLength(record.getNSName());
    }

    @Override
    public int encodeRecordData(NSResourceRecord record, ByteBuffer buf) {
        int length = StandardFormatUtils.getEncodedDomainNameLength(record.getNSName());
        StandardFormatUtils.encodeUInt16(length, buf);
        StandardFormatUtils.encodeDomainName(record.getNSName(), buf);

        return length;
    }
}
