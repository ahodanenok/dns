package ahodanenok.dns.core.record;

public class StandardRecordType {

    public static final RecordType A = new RecordType("A", 1);
    public static final RecordType NS = new RecordType("NS", 2);
    public static final RecordType MD = new RecordType("MD", 3);
    public static final RecordType MF = new RecordType("MF", 4);
    public static final RecordType CNAME = new RecordType("CNAME", 5);
    public static final RecordType SOA = new RecordType("SOA", 6);
    public static final RecordType MB = new RecordType("SOA", 7);
    public static final RecordType MG = new RecordType("MG", 8);
    public static final RecordType MR = new RecordType("MR", 9);
    public static final RecordType NULL = new RecordType("NULL", 10);
    public static final RecordType WKS = new RecordType("WKS", 11);
    public static final RecordType PTR = new RecordType("PTR", 12);
    public static final RecordType HINFO = new RecordType("HINFO", 13);
    public static final RecordType MINFO = new RecordType("MINFO", 14);
    public static final RecordType MX = new RecordType("MX", 15);
    public static final RecordType TXT = new RecordType("TXT", 16);
}
