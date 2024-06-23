package ahodanenok.dns.core.model;

public enum StandardRecordClass implements RecordClass {

    IN("IN", 1),
    CS("CS", 2),
    CH("CH", 3),
    HS("HS", 4);

    private final String name;
    private final int code;

    StandardRecordClass(String name, int code) {
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
