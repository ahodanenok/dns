package ahodanenok.dns.core.model;

public enum StandardRecordType implements RecordType {

    A("A", 1),
    NS("NS", 2),
    MD("MD", 3),
    MF("MF", 4),
    CNAME("CNAME", 5),
    SOA("SOA", 6),
    MB("SOA", 7),
    MG("MG", 8),
    MR("MR", 9),
    NULL("NULL", 10),
    WKS("WKS", 11),
    PTR("PTR", 12),
    HINFO("HINFO", 13),
    MINFO("MINFO", 14),
    MX("MX", 15),
    TXT("TXT", 16);

    private final String name;
    private final int code;

    StandardRecordType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCode() {
        return code;
    }
}
