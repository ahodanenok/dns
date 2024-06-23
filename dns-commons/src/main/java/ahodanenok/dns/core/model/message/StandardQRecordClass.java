package ahodanenok.dns.core.model.message;

public enum StandardQRecordClass implements QRecordClass {

    ANY("*", 255);

    private final String name;
    private final int code;

    StandardQRecordClass(String name, int code) {
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
