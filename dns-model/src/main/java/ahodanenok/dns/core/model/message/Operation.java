package ahodanenok.dns.core.model.message;

public enum Operation {

    QUERY(0),
    INVERSE_QUERY(1),
    STATUS(2);

    private final int code;

    Operation(int code) {
        this.code = code;
    }

    public static Operation fromCode(int code) {
        for (Operation operation : values()) {
            if (operation.code == code) {
                return operation;
            }
        }

        return null;
    }
}
