package ahodanenok.dns.core.message.format.standard;

import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.message.format.ResourceRecordEncoder;

public interface StandardResourceRecordEncoder<R extends ResourceRecord>
        extends ResourceRecordEncoder<R> {

}
