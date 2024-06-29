package ahodanenok.dns.core.message;

public class StandardQRecordType {

    public static final QRecordType AXFR = new QRecordType("AXFR", 252);
    public static final QRecordType MAILB = new QRecordType("MAILB", 253);
    public static final QRecordType MAILA = new QRecordType("MAILA", 254);
    public static final QRecordType ANY = new QRecordType("*", 255);
}