package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public final class NSResourceRecord extends AbstractResourceRecord {

    private DomainName nsName;

    @Override
    public String getType() {
        return "NS";
    }

    public DomainName getNSName() {
        return nsName;
    }

    public void setNSName(DomainName nsName) {
        this.nsName = nsName;
    }
}
