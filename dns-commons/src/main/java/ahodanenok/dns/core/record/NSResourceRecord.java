package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public final class NSResourceRecord extends AbstractResourceRecord {

    private DomainName nsName;

    @Override
    public RecordType getType() {
        return StandardRecordType.NS;
    }

    public DomainName getNSName() {
        return nsName;
    }

    public void setNSName(DomainName nsName) {
        this.nsName = nsName;
    }
}
