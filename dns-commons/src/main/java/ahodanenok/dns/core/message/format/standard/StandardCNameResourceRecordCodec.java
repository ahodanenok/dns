package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.message.format.ResourceRecordDecoder;
import ahodanenok.dns.core.message.format.ResourceRecordEncoder;
import ahodanenok.dns.core.question.QRecordType;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.StandardRecordType;

public final class StandardCNameResourceRecordCodec
        implements StandardResourceRecordDecoder<CNameResourceRecord>, ResourceRecordEncoder<CNameResourceRecord> {

    @Override
    public CNameResourceRecord createInitialRecord() {
        return new CNameResourceRecord();
    }

    @Override
    public QRecordType getRecordType() {
        return StandardRecordType.CNAME;
    }

    @Override
    public void decodeRecordData(ByteBuffer buf, int length, StandardMessageDecoderState state, CNameResourceRecord target) {
        // todo: use length
        target.setCanonicalName(state.domainNameDecoder.decode(buf));
    }
}
