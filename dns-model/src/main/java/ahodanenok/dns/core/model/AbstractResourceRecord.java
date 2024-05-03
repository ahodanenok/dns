package ahodanenok.dns.core.model;

public abstract class AbstractResourceRecord implements ResourceRecord {

    private DomainName name;
    private String rclass;
    private int ttl;

    @Override
    public DomainName getName() {
        return name;
    }

    @Override
    public void setName(DomainName name) {
        this.name = name;
    }

    @Override
    public String getRClass() {
        return rclass;
    }

    @Override
    public void setRClass(String rclass) {
        this.rclass = rclass;
    }

    @Override
    public int getTtl() {
        return ttl;
    }

    @Override
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
