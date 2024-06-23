package ahodanenok.dns.core.model.message.format.standard;

import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.message.format.ResourceRecordDecoder;

public interface StandardResourceRecordDecoder<R extends ResourceRecord>
        extends ResourceRecordDecoder<R, StandardMessageDecoderState> {

}
