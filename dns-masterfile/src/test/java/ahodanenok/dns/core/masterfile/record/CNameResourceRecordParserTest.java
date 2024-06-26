package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;

import static org.junit.jupiter.api.Assertions.*;

public class CNameResourceRecordParserTest {

    @Test
    @DisplayName("Parse CNAME record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new CNameResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/cname-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(3600, record.getTtl());
        assertEquals(DomainName.parse("test-001.cname."), record.getName());

        CNameResourceRecord cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-001.cname."), cnameRecord.getCanonicalName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse CNAME record: <domain-name> <class> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new CNameResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);
        config.setDefaultTtl(1500);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/cname-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(1500, record.getTtl());
        assertEquals(DomainName.parse("test-002.cname."), record.getName());

        CNameResourceRecord cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-002.cname."), cnameRecord.getCanonicalName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse CNAME record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new CNameResourceRecordParser());
        config.setDefaultTtl(200);
        config.setDefaultRClass(StandardRecordClass.CH);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/cname-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(200, record.getTtl());
        assertEquals(DomainName.parse("test-003.cname."), record.getName());

        CNameResourceRecord cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-003.cname."), cnameRecord.getCanonicalName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse CNAME record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new CNameResourceRecordParser());
        config.setDefaultRClass(StandardRecordClass.CS);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/cname-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(50000, record.getTtl());
        assertEquals(DomainName.parse("test-004.cname."), record.getName());

        CNameResourceRecord cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-004.cname."), cnameRecord.getCanonicalName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse CNAME record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new CNameResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.CH);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/cname-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(25, record.getTtl());
        assertEquals(DomainName.parse("test-005.cname."), record.getName());

        CNameResourceRecord cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-005.cname."), cnameRecord.getCanonicalName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple CNAME records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new CNameResourceRecordParser());
        config.setDefaultTtl(180000);
        config.setDefaultRClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.CH);
        config.addRecordClass(StandardRecordClass.CS);
        config.addRecordClass(StandardRecordClass.HS);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/cname-006.txt"));

        ResourceRecord record;
        CNameResourceRecord cnameRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(1200, record.getTtl());
        assertEquals(DomainName.parse("test-006-1.cname."), record.getName());
        cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-006-1.cname."), cnameRecord.getCanonicalName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(720000, record.getTtl());
        assertEquals(DomainName.parse("test-006-2.cname."), record.getName());
        cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-006-2.cname."), cnameRecord.getCanonicalName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(720000, record.getTtl());
        assertEquals(DomainName.parse("test-006-3.cname."), record.getName());
        cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-006-3.cname."), cnameRecord.getCanonicalName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(245, record.getTtl());
        assertEquals(DomainName.parse("test-006-4.cname."), record.getName());
        cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-006-4.cname."), cnameRecord.getCanonicalName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.CNAME, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(245, record.getTtl());
        assertEquals(DomainName.parse("test-006-5.cname."), record.getName());
        cnameRecord = assertInstanceOf(CNameResourceRecord.class, record);
        assertEquals(DomainName.parse("canonical.test-006-5.cname."), cnameRecord.getCanonicalName());

        assertFalse(records.hasNext());
        records.close();
    }
}
