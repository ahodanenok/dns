package ahodanenok.dns.core.masterfile;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.record.CNameResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.NSResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.SOAResourceRecordParser;
import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.Zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterFileZoneSupplierTest {

    @Test
    public void testGet() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordClass("IN");
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordParser(new CNameResourceRecordParser());

        MasterFileParser parser = new MasterFileParser(config);
        MasterFileZoneSupplier supplier = new MasterFileZoneSupplier(parser, FileUtils.getTestFilePath("sp.zone"));
        Zone zone = supplier.get();

        assertEquals(DomainName.of("abc.local."), zone.getName());
        assertEquals(5, zone.getRecords().size());

        ResourceRecord r;

        r = zone.getRecords().get(0);
        assertEquals(DomainName.of("abc.local."), r.getName());
        assertEquals("SOA", r.getType());
        assertEquals("IN", r.getRClass());
        assertEquals(3600, r.getTtl());

        r = zone.getRecords().get(1);
        assertEquals(DomainName.of("a.abc.local."), r.getName());
        assertEquals("NS", r.getType());
        assertEquals("IN", r.getRClass());
        assertEquals(200, r.getTtl());

        r = zone.getRecords().get(2);
        assertEquals(DomainName.of("b.abc.local."), r.getName());
        assertEquals("NS", r.getType());
        assertEquals("IN", r.getRClass());
        assertEquals(300, r.getTtl());

        r = zone.getRecords().get(3);
        assertEquals(DomainName.of("c.abc.local."), r.getName());
        assertEquals("NS", r.getType());
        assertEquals("IN", r.getRClass());
        assertEquals(400, r.getTtl());

        r = zone.getRecords().get(4);
        assertEquals(DomainName.of("d.abc.local."), r.getName());
        assertEquals("CNAME", r.getType());
        assertEquals("IN", r.getRClass());
        assertEquals(86400, r.getTtl());
    }

    @Test
    public void testGetMultiple() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordClass("IN");
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordParser(new CNameResourceRecordParser());

        MasterFileParser parser = new MasterFileParser(config);
        MasterFileZoneSupplier supplier = new MasterFileZoneSupplier(parser, FileUtils.getTestFilePath("sp.zone"));

        Zone zone;

        zone = supplier.get();
        assertEquals(DomainName.of("abc.local."), zone.getName());
        assertEquals(5, zone.getRecords().size());

        zone = supplier.get();
        assertEquals(DomainName.of("abc.local."), zone.getName());
        assertEquals(5, zone.getRecords().size());
    }
}
