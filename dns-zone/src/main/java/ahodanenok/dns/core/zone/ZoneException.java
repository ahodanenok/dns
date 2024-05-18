package ahodanenok.dns.core.zone;

public class ZoneException extends RuntimeException {

    public ZoneException(String msg) {
        super(msg);
    }

    public ZoneException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
