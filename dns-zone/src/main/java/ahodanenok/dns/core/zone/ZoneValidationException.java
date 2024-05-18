package ahodanenok.dns.core.zone;

import java.util.List;

public class ZoneValidationException extends ZoneException {

    private final List<String> errors;

    public ZoneValidationException(List<String> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
