package ahodanenok.dns.core.model.message.format;

public class MessageFormatException extends RuntimeException {

    public MessageFormatException(String message) {
        super(message);
    }

    public MessageFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
