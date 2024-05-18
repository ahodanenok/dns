package ahodanenok.dns.core.zone;

import java.util.List;

import ahodanenok.dns.core.model.ResourceRecord;

public final class DummyZoneValidator implements ZoneValidator {

    public static final DummyZoneValidator INSTANCE = new DummyZoneValidator();

    @Override
    public void validateNext(ResourceRecord record) {
        /* no-op */
    }

    @Override
    public void validateFinal() {
        /* no-op */
    }

    @Override
    public List<String> getErrors() {
        return List.of();
    }
}
