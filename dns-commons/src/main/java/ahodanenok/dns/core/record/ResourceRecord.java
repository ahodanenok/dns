package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public interface ResourceRecord {

    DomainName getName();

    void setName(DomainName name);

    String getType();

    String getRClass();

    void setRClass(String rclass);

    int getTtl();

    void setTtl(int ttl);
}
