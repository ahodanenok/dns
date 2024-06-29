package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public abstract class AbstractResourceRecord implements ResourceRecord {

    private DomainName name;
    private RecordClass rclass;
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
    public RecordClass getRClass() {
        return rclass;
    }

    @Override
    public void setRClass(RecordClass rclass) {
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
