package ahodanenok.dns.core.model.message;

public enum OperationCode {

    QUERY;

    public static OperationCode of(int code) {
        return QUERY;
    }
}
