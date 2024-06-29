package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public final class CNameResourceRecord extends AbstractResourceRecord {

    private DomainName canonicalName;

    @Override
    public RecordType getType() {
        return StandardRecordType.CNAME;
    }

    public DomainName getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(DomainName canonicalName) {
        this.canonicalName = canonicalName;
    }
}
