package ahodanenok.dns.core.model;

public final class CNameResourceRecord extends AbstractResourceRecord {

    private DomainName canonicalName;

    @Override
    public String getType() {
        return "CNAME";
    }

    public DomainName getCanonicalName() {
        return canonicalName;
    }

    public void setCanonicalName(DomainName canonicalName) {
        this.canonicalName = canonicalName;
    }
}
