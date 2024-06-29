package ahodanenok.dns.core.masterfile;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.record.CNameResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.NSResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.SOAResourceRecordParser;
import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;
import ahodanenok.dns.core.zone.Zone;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MasterFileZoneSupplierTest {

    @Test
    public void testGet() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordParser(new CNameResourceRecordParser());

        MasterFileParser parser = new MasterFileParser(config);
        MasterFileZoneSupplier supplier = new MasterFileZoneSupplier(parser, FileUtils.getTestFilePath("sp.zone"));
        Zone zone = supplier.get();

        assertEquals(DomainName.parse("abc.local."), zone.getName());
        assertEquals(5, zone.getRecords().size());

        ResourceRecord r;

        r = zone.getRecords().get(0);
        assertEquals(DomainName.parse("abc.local."), r.getName());
        assertEquals(StandardRecordType.SOA, r.getType());
        assertEquals(StandardRecordClass.IN, r.getRClass());
        assertEquals(3600, r.getTtl());

        r = zone.getRecords().get(1);
        assertEquals(DomainName.parse("a.abc.local."), r.getName());
        assertEquals(StandardRecordType.NS, r.getType());
        assertEquals(StandardRecordClass.IN, r.getRClass());
        assertEquals(200, r.getTtl());

        r = zone.getRecords().get(2);
        assertEquals(DomainName.parse("b.abc.local."), r.getName());
        assertEquals(StandardRecordType.NS, r.getType());
        assertEquals(StandardRecordClass.IN, r.getRClass());
        assertEquals(300, r.getTtl());

        r = zone.getRecords().get(3);
        assertEquals(DomainName.parse("c.abc.local."), r.getName());
        assertEquals(StandardRecordType.NS, r.getType());
        assertEquals(StandardRecordClass.IN, r.getRClass());
        assertEquals(400, r.getTtl());

        r = zone.getRecords().get(4);
        assertEquals(DomainName.parse("d.abc.local."), r.getName());
        assertEquals(StandardRecordType.CNAME, r.getType());
        assertEquals(StandardRecordClass.IN, r.getRClass());
        assertEquals(86400, r.getTtl());
    }

    @Test
    public void testGetMultiple() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordParser(new CNameResourceRecordParser());

        MasterFileParser parser = new MasterFileParser(config);
        MasterFileZoneSupplier supplier = new MasterFileZoneSupplier(parser, FileUtils.getTestFilePath("sp.zone"));

        Zone zone;

        zone = supplier.get();
        assertEquals(DomainName.parse("abc.local."), zone.getName());
        assertEquals(5, zone.getRecords().size());

        zone = supplier.get();
        assertEquals(DomainName.parse("abc.local."), zone.getName());
        assertEquals(5, zone.getRecords().size());
    }
}
