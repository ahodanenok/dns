package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.model.CNameResourceRecord;
import ahodanenok.dns.core.model.message.format.ResourceRecordDecoder;
import ahodanenok.dns.core.model.message.format.ResourceRecordEncoder;

public final class StandardCNameResourceRecordCodec
        implements StandardResourceRecordDecoder<CNameResourceRecord>, ResourceRecordEncoder<CNameResourceRecord> {

    @Override
    public CNameResourceRecord createInitialRecord() {
        return new CNameResourceRecord();
    }

    @Override
    public String getRecordType() {
        return "CNAME";
    }

    @Override
    public void decodeRData(ByteBuffer buf, StandardMessageDecoderState state, CNameResourceRecord target) {

    }
}
