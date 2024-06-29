package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.AResourceRecord;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;

import static org.junit.jupiter.api.Assertions.*;

public class AResourceRecordParserTest {

    @Test
    @DisplayName("Parse A record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new AResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/a-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(54321, record.getTtl());
        assertEquals(DomainName.parse("test-001.address."), record.getName());

        AResourceRecord aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("173.54.23.123", aRecord.getAddress());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse A record: <domain-name> <class> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new AResourceRecordParser());
        config.addRecordClass(StandardRecordClass.HS);
        config.setDefaultTtl(321);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/a-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(321, record.getTtl());
        assertEquals(DomainName.parse("test-002.address."), record.getName());

        AResourceRecord aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("253.234.123.6", aRecord.getAddress());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse A record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new AResourceRecordParser());
        config.setDefaultTtl(3600);
        config.setDefaultRClass(StandardRecordClass.CH);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/a-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(3600, record.getTtl());
        assertEquals(DomainName.parse("test-003.address."), record.getName());

        AResourceRecord aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("100.101.102.103", aRecord.getAddress());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse A record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new AResourceRecordParser());
        config.setDefaultRClass(StandardRecordClass.CS);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/a-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(48400, record.getTtl());
        assertEquals(DomainName.parse("test-004.address."), record.getName());

        AResourceRecord aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("1.2.3.4", aRecord.getAddress());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse A record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new AResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.CH);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/a-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(12, record.getTtl());
        assertEquals(DomainName.parse("test-005.address."), record.getName());

        AResourceRecord aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("23.76.22.15", aRecord.getAddress());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple A records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new AResourceRecordParser());
        config.setDefaultTtl(333444);
        config.setDefaultRClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.CH);
        config.addRecordClass(StandardRecordClass.CS);
        config.addRecordClass(StandardRecordClass.HS);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/a-006.txt"));

        ResourceRecord record;
        AResourceRecord aRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(5231, record.getTtl());
        assertEquals(DomainName.parse("test-006-1.address."), record.getName());
        aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("11.234.2.55", aRecord.getAddress());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(720000, record.getTtl());
        assertEquals(DomainName.parse("test-006-2.address."), record.getName());
        aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("8.25.4.89", aRecord.getAddress());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(720000, record.getTtl());
        assertEquals(DomainName.parse("test-006-3.address."), record.getName());
        aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("192.168.5.100", aRecord.getAddress());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(3246, record.getTtl());
        assertEquals(DomainName.parse("test-006-4.address."), record.getName());
        aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("127.0.0.1", aRecord.getAddress());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.A, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(3246, record.getTtl());
        assertEquals(DomainName.parse("test-006-5.address."), record.getName());
        aRecord = assertInstanceOf(AResourceRecord.class, record);
        assertEquals("201.22.34.112", aRecord.getAddress());

        assertFalse(records.hasNext());
        records.close();
    }
}
