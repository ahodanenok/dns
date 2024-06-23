package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.CNameResourceRecord;
import ahodanenok.dns.core.model.StandardRecordType;
import ahodanenok.dns.core.model.message.QRecordType;
import ahodanenok.dns.core.model.message.format.ResourceRecordDecoder;
import ahodanenok.dns.core.model.message.format.ResourceRecordEncoder;

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
