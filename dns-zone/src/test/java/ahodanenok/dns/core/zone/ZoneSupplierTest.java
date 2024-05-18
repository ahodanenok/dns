package ahodanenok.dns.core.zone;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.NSResourceRecord;
import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.SOAResourceRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZoneSupplierTest {

    @Test
    public void testGet() {
        SOAResourceRecord soaRecord = new SOAResourceRecord();
        soaRecord.setName(DomainName.of("a.b.c."));
        NSResourceRecord r1 = new NSResourceRecord();
        NSResourceRecord r2 = new NSResourceRecord();

        ZoneSupplier supplier = new ZoneSupplier(new DummyZoneValidator()) {
            @Override
            public Iterable<ResourceRecord> getIterable() {
                return List.of(soaRecord, r1, r2);
            }
        };

        Zone zone = supplier.get();
        assertEquals(DomainName.of("a.b.c."), zone.getName());
        assertEquals(3, zone.getRecords().size());
        assertSame(soaRecord, zone.getRecords().get(0));
        assertSame(r1, zone.getRecords().get(1));
        assertSame(r2, zone.getRecords().get(2));
    }

    @Test
    public void testGetInvalid() {
        SOAResourceRecord soaRecord = new SOAResourceRecord();
        soaRecord.setName(DomainName.of("a.b.c."));
        NSResourceRecord r1 = new NSResourceRecord();

        ZoneValidator validator = new ZoneValidator() {

            private final List<String> errors = new ArrayList<>();
            private int n = 1;

            @Override
            public void validateNext(ResourceRecord record) {
                errors.add("error " + n++);
            }

            @Override
            public void validateFinal() {
                errors.add("error " + n++);
            }

            @Override
            public List<String> getErrors() {
                return errors;
            }
        };

        ZoneSupplier supplier = new ZoneSupplier(validator) {
            @Override
            public Iterable<ResourceRecord> getIterable() {
                return List.of(soaRecord, r1);
            }
        };

        ZoneValidationException ex = assertThrows(
            ZoneValidationException.class, () -> supplier.get());
        assertEquals(List.of("error 1", "error 2", "error 3"), ex.getErrors());
    }
}