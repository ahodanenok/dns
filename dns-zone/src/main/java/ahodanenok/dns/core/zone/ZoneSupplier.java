package ahodanenok.dns.core.zone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import ahodanenok.dns.core.record.ResourceRecord;

public abstract class ZoneSupplier implements Supplier<Zone> {

    private ZoneValidator validator;

    public ZoneSupplier() {
        this(DummyZoneValidator.INSTANCE);
    }

    public ZoneSupplier(ZoneValidator validator) {
        this.validator = validator;
    }

    public void setValidator(ZoneValidator validator) {
        this.validator = validator;
    }

    @Override
    public final Zone get() {
        List<ResourceRecord> records = new ArrayList<>();
        for (ResourceRecord record : getIterable()) {
            validator.validateNext(record);
            records.add(record);
        }
        validator.validateFinal();

        List<String> errors = validator.getErrors();
        if (!errors.isEmpty()) {
            throw new ZoneValidationException(errors);
        }

        return new Zone(records);
    }

    protected abstract Iterable<ResourceRecord> getIterable();
}
