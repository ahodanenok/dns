package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordType;
import ahodanenok.dns.core.message.format.ResourceRecordDecoder;
import ahodanenok.dns.core.message.format.ResourceRecordEncoder;

public final class StandardCNameResourceRecordCodec
        implements StandardResourceRecordDecoder<CNameResourceRecord>, StandardResourceRecordEncoder<CNameResourceRecord> {

    @Override
    public CNameResourceRecord createInitialRecord() {
        return new CNameResourceRecord();
    }

    @Override
    public RecordType getRecordType() {
        return StandardRecordType.CNAME;
    }

    @Override
    public void decodeRecordData(ByteBuffer buf, int length, StandardMessageDecoderState state, CNameResourceRecord record) {
        // todo: use length
        record.setCanonicalName(state.domainNameDecoder.decode(buf));
    }

    @Override
    public int getExpectedDataLength(CNameResourceRecord record) {
        return StandardFormatUtils.getEncodedDomainNameLength(record.getCanonicalName());
    }

    @Override
    public int encodeRecordData(CNameResourceRecord record, ByteBuffer buf) {
        int length = StandardFormatUtils.getEncodedDomainNameLength(record.getCanonicalName());
        StandardFormatUtils.encodeUInt16(length, buf);
        StandardFormatUtils.encodeDomainName(record.getCanonicalName(), buf);

        return length;
    }
}
