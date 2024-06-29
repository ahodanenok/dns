package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public interface ResourceRecord {

    DomainName getName();

    void setName(DomainName name);

    RecordType getType();

    RecordClass getRClass();

    void setRClass(RecordClass rclass);

    int getTtl();

    void setTtl(int ttl);
}
