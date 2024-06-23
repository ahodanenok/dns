package ahodanenok.dns.core.message;

import ahodanenok.dns.core.DomainName;

public final class Question {

    private final DomainName qname;
    private final QRecordType qtype;
    private final QRecordClass qclass;

    public Question(DomainName qname, QRecordType qtype, QRecordClass qclass) {
        this.qname = qname;
        this.qtype = qtype;
        this.qclass = qclass;
    }

    public DomainName getQName() {
        return qname;
    }

    public QRecordType getQType() {
        return qtype;
    }

    public QRecordClass getQClass() {
        return qclass;
    }
}
