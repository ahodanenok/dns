package ahodanenok.dns.core.model;

public interface ResourceRecord {

    DomainName getName();

    void setName(DomainName name);

    String getType();

    String getRClass();

    void setRClass(String rclass);

    int getTtl();

    void setTtl(int ttl);
}
