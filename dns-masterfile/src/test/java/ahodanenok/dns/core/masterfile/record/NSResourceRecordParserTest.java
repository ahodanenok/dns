package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.NSResourceRecord;
import ahodanenok.dns.core.model.ResourceRecord;

import static org.junit.jupiter.api.Assertions.*;

public class NSResourceRecordParserTest {

    @Test
    @DisplayName("Parse NS record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordClass("CS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ns-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(4800, record.getTtl());
        assertEquals(DomainName.parse("test-001."), record.getName());

        NSResourceRecord nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-001."), nsRecord.getNSName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse NS record: <domain-name> <class> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordClass("IN");
        config.setDefaultTtl(200);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ns-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(200, record.getTtl());
        assertEquals(DomainName.parse("test-002."), record.getName());

        NSResourceRecord nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-002."), nsRecord.getNSName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse NS record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new NSResourceRecordParser());
        config.setDefaultTtl(500);
        config.setDefaultRClass("CH");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ns-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("CH", record.getRClass());
        assertEquals(500, record.getTtl());
        assertEquals(DomainName.parse("test-003."), record.getName());

        NSResourceRecord nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-003."), nsRecord.getNSName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse NS record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new NSResourceRecordParser());
        config.setDefaultRClass("HS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ns-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("HS", record.getRClass());
        assertEquals(32, record.getTtl());
        assertEquals(DomainName.parse("test-004."), record.getName());

        NSResourceRecord nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-004."), nsRecord.getNSName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse NS record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new NSResourceRecordParser());
        config.addRecordClass("CH");
        config.addRecordClass("CS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ns-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("CH", record.getRClass());
        assertEquals(550, record.getTtl());
        assertEquals(DomainName.parse("test-005."), record.getName());

        NSResourceRecord nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-005."), nsRecord.getNSName());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple NS records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new NSResourceRecordParser());
        config.setDefaultTtl(7200);
        config.setDefaultRClass("IN");
        config.addRecordClass("IN");
        config.addRecordClass("CH");
        config.addRecordClass("CS");
        config.addRecordClass("HS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/ns-006.txt"));

        ResourceRecord record;
        NSResourceRecord nsRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(1200, record.getTtl());
        assertEquals(DomainName.parse("test-006-1."), record.getName());
        nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-006-1."), nsRecord.getNSName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(1200, record.getTtl());
        assertEquals(DomainName.parse("test-006-2."), record.getName());
        nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-006-2."), nsRecord.getNSName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(720000, record.getTtl());
        assertEquals(DomainName.parse("test-006-3."), record.getName());
        nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-006-3."), nsRecord.getNSName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("HS", record.getRClass());
        assertEquals(720000, record.getTtl());
        assertEquals(DomainName.parse("test-006-4."), record.getName());
        nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-006-4."), nsRecord.getNSName());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("NS", record.getType());
        assertEquals("HS", record.getRClass());
        assertEquals(245, record.getTtl());
        assertEquals(DomainName.parse("test-006-5."), record.getName());
        nsRecord = assertInstanceOf(NSResourceRecord.class, record);
        assertEquals(DomainName.parse("ns.test-006-5."), nsRecord.getNSName());

        assertFalse(records.hasNext());
        records.close();
    }
}
