package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.PTRResourceRecord;
import ahodanenok.dns.core.model.ResourceRecord;

import static org.junit.jupiter.api.Assertions.*;

public class PTRResourceRecordParserTest {

    @Test
    @DisplayName("Parse PTR record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new PTRResourceRecordParser());
        config.addRecordClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ptr-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(3600, record.getTtl());
        assertEquals(DomainName.parse("22.0.2.10.IN-ADDR.ARPA."), record.getName());

        PTRResourceRecord ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-001."), ptrRecord.getTargetName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse PTR record: <domain-name> <class> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new PTRResourceRecordParser());
        config.addRecordClass("HS");
        config.setDefaultTtl(15000);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ptr-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("HS", record.getRClass());
        assertEquals(15000, record.getTtl());
        assertEquals(DomainName.parse("10.IN-ADDR.ARPA."), record.getName());

        PTRResourceRecord ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-002."), ptrRecord.getTargetName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse PTR record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new PTRResourceRecordParser());
        config.setDefaultTtl(500);
        config.setDefaultRClass("CH");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ptr-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("CH", record.getRClass());
        assertEquals(500, record.getTtl());
        assertEquals(DomainName.parse("some.address.test-003."), record.getName());

        PTRResourceRecord ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-003."), ptrRecord.getTargetName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse PTR record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new PTRResourceRecordParser());
        config.setDefaultRClass("CS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ptr-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(86400, record.getTtl());
        assertEquals(DomainName.parse("1.2.3.4.in-addr.arpa."), record.getName());

        PTRResourceRecord ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-004."), ptrRecord.getTargetName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse PTR record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new PTRResourceRecordParser());
        config.addRecordClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ptr-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(678, record.getTtl());
        assertEquals(DomainName.parse("10.20.IN-ADDR.ARPA."), record.getName());

        PTRResourceRecord ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-005."), ptrRecord.getTargetName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple NS records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new PTRResourceRecordParser());
        config.setDefaultTtl(720);
        config.setDefaultRClass("CH");
        config.addRecordClass("IN");
        config.addRecordClass("CH");
        config.addRecordClass("CS");
        config.addRecordClass("HS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ptr-006.txt"));

        ResourceRecord record;
        PTRResourceRecord ptrRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(720, record.getTtl());
        assertEquals(DomainName.parse("10.IN-ADDR.ARPA."), record.getName());
        ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-006-1."), ptrRecord.getTargetName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(36000, record.getTtl());
        assertEquals(DomainName.parse("20.IN-ADDR.ARPA."), record.getName());
        ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-006-2."), ptrRecord.getTargetName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(36000, record.getTtl());
        assertEquals(DomainName.parse("30.IN-ADDR.ARPA."), record.getName());
        ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-006-3."), ptrRecord.getTargetName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(5450, record.getTtl());
        assertEquals(DomainName.parse("40.IN-ADDR.ARPA."), record.getName());
        ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-006-4."), ptrRecord.getTargetName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("PTR", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(32, record.getTtl());
        assertEquals(DomainName.parse("50.IN-ADDR.ARPA."), record.getName());
        ptrRecord = assertInstanceOf(PTRResourceRecord.class, record);
        assertEquals(DomainName.parse("ptr.test-006-5."), ptrRecord.getTargetName());

        assertFalse(records.hasNext());
        records.close();
    }
}
