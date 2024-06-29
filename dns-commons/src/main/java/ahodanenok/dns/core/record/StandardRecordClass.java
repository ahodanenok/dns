package ahodanenok.dns.core.record;

public interface StandardRecordClass {

    public static final RecordClass IN = new RecordClass("IN", 1);
    public static final RecordClass CS = new RecordClass("CS", 2);
    public static final RecordClass CH = new RecordClass("CH", 3);
    public static final RecordClass HS = new RecordClass("HS", 4);
}
