package ahodanenok.dns.core.message;

public enum StandardQRecordType implements QRecordType {

    AXFR("AXFR", 252),
    MAILB("MAILB", 253),
    MAILA("MAILA", 254),
    ANY("*", 255);

    private final String name;
    private final int code;

    StandardQRecordType(String name, int code) {
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