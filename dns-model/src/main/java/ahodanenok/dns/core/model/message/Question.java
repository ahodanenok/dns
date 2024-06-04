package ahodanenok.dns.core.model.message;

import ahodanenok.dns.core.model.DomainName;

public final class Question {

    private final DomainName qname;
    private final String qtype;
    private final String qclass;

    public Question(DomainName qname, String qtype, String qclass) {
        this.qname = qname;
        this.qtype = qtype;
        this.qclass = qclass;
    }

    public DomainName getQName() {
        return qname;
    }

    public String getQType() {
        return qtype;
    }

    public String getQClass() {
        return qclass;
    }
}
