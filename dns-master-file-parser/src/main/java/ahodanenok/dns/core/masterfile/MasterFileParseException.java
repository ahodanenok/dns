package ahodanenok.dns.core.masterfile;

public class MasterFileParseException extends RuntimeException {

    public MasterFileParseException(String msg) {
        super(msg);
    }

    public MasterFileParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
