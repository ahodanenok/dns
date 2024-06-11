package ahodanenok.dns.core.model.message;

public enum ResponseStatus {

    OK(0),
    FORMAT_ERROR(1),
    SERVER_FAILURE(2),
    NAME_ERROR(3),
    NOT_IMPLEMENTED(4),
    REFUSED(5);

    private final int code;

    ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ResponseStatus fromCode(int code) {
        for (ResponseStatus response : values()) {
            if (response.code == code) {
                return response;
            }
        }

        return null;
    }
}
