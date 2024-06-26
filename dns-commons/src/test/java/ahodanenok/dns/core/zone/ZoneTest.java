package ahodanenok.dns.core.zone;

import java.util.List;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.NSResourceRecord;
import ahodanenok.dns.core.record.SOAResourceRecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ZoneTest {

    @Test
    public void testGetName() {
        SOAResourceRecord soaRecord = new SOAResourceRecord();
        soaRecord.setName(DomainName.parse("soa.test."));

        assertEquals(DomainName.parse("soa.test."),
            new Zone(List.of(soaRecord, new CNameResourceRecord(), new NSResourceRecord())).getName());
        assertEquals(DomainName.parse("soa.test."),
            new Zone(List.of(new CNameResourceRecord(), soaRecord, new NSResourceRecord())).getName());
        assertEquals(DomainName.parse("soa.test."),
            new Zone(List.of(new NSResourceRecord(), new CNameResourceRecord(), soaRecord)).getName());
    }

    @Test
    public void testGetRecords() {
        SOAResourceRecord r1 = new SOAResourceRecord();
        NSResourceRecord r2 = new NSResourceRecord();
        CNameResourceRecord r3 = new CNameResourceRecord();
        Zone zone = new Zone(List.of(r1, r2, r3));
        assertEquals(3, zone.getRecords().size());
        assertSame(r1, zone.getRecords().get(0));
        assertSame(r2, zone.getRecords().get(1));
        assertSame(r3, zone.getRecords().get(2));
    }
}
