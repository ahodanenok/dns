package ahodanenok.dns.core.message.format.standard;

import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.message.format.ResourceRecordDecoder;

public interface StandardResourceRecordDecoder<R extends ResourceRecord>
        extends ResourceRecordDecoder<R, StandardMessageDecoderState> {

}
