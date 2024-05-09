package ahodanenok.dns.core.model;

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
