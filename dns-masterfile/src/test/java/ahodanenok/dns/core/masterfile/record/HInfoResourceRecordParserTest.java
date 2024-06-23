package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.HInfoResourceRecord;
import ahodanenok.dns.core.record.ResourceRecord;

import static org.junit.jupiter.api.Assertions.*;

public class HInfoResourceRecordParserTest {

    @Test
    @DisplayName("Parse HINFO record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new HInfoResourceRecordParser());
        config.addRecordClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/hinfo-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(4800, record.getTtl());
        assertEquals(DomainName.parse("test-001.hinfo."), record.getName());

        HInfoResourceRecord hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("DEC-KS10", hinfoRecord.getCPU());
        assertEquals("UNIX", hinfoRecord.getOS());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse HINFO record: <domain-name> <class> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new HInfoResourceRecordParser());
        config.addRecordClass("CS");
        config.setDefaultTtl(3200);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/hinfo-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(3200, record.getTtl());
        assertEquals(DomainName.parse("test-002.hinfo."), record.getName());

        HInfoResourceRecord hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("IBM-4381", hinfoRecord.getCPU());
        assertEquals("WIN32", hinfoRecord.getOS());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse HINFO record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new HInfoResourceRecordParser());
        config.setDefaultTtl(53000);
        config.setDefaultRClass("CH");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/hinfo-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("CH", record.getRClass());
        assertEquals(53000, record.getTtl());
        assertEquals(DomainName.parse("test-003.hinfo."), record.getName());

        HInfoResourceRecord hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("HP3000", hinfoRecord.getCPU());
        assertEquals("XDE", hinfoRecord.getOS());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse HINFO record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new HInfoResourceRecordParser());
        config.setDefaultRClass("CS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/hinfo-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(3411267, record.getTtl());
        assertEquals(DomainName.parse("test-004.hinfo."), record.getName());

        HInfoResourceRecord hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("MAC-II", hinfoRecord.getCPU());
        assertEquals("NOS-2", hinfoRecord.getOS());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse HINFO record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new HInfoResourceRecordParser());
        config.addRecordClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/hinfo-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(9, record.getTtl());
        assertEquals(DomainName.parse("test-005.hinfo."), record.getName());

        HInfoResourceRecord hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("TRS-80", hinfoRecord.getCPU());
        assertEquals("SUN", hinfoRecord.getOS());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple HINFO records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new HInfoResourceRecordParser());
        config.setDefaultTtl(86400);
        config.setDefaultRClass("IN");
        config.addRecordClass("IN");
        config.addRecordClass("CH");
        config.addRecordClass("CS");
        config.addRecordClass("HS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/hinfo-006.txt"));

        ResourceRecord record;
        HInfoResourceRecord hinfoRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(86400, record.getTtl());
        assertEquals(DomainName.parse("test-006-1.hinfo."), record.getName());
        hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("ZENITH-148", hinfoRecord.getCPU());
        assertEquals("TENEX", hinfoRecord.getOS());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(86400, record.getTtl());
        assertEquals(DomainName.parse("test-006-2.hinfo."), record.getName());
        hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("VAX", hinfoRecord.getCPU());
        assertEquals("NEXUS", hinfoRecord.getOS());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(3452, record.getTtl());
        assertEquals(DomainName.parse("test-006-3.hinfo."), record.getName());
        hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("PLI", hinfoRecord.getCPU());
        assertEquals("FUZZ", hinfoRecord.getOS());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(111111, record.getTtl());
        assertEquals(DomainName.parse("test-006-4.hinfo."), record.getName());
        hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("IMAGEN-8/300", hinfoRecord.getCPU());
        assertEquals("AMIGA-OS-3.1", hinfoRecord.getOS());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("HINFO", record.getType());
        assertEquals("CH", record.getRClass());
        assertEquals(6534, record.getTtl());
        assertEquals(DomainName.parse("test-006-5.hinfo."), record.getName());
        hinfoRecord = assertInstanceOf(HInfoResourceRecord.class, record);
        assertEquals("HONEYWELL-DPS-6", hinfoRecord.getCPU());
        assertEquals("CHRYSALIS", hinfoRecord.getOS());

        assertFalse(records.hasNext());
        records.close();
    }
}
