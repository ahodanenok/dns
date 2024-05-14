package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.SOAResourceRecord;
import ahodanenok.dns.core.model.ResourceRecord;

import static org.junit.jupiter.api.Assertions.*;

public class SOAResourceRecordParserTest {

    @Test
    @DisplayName("Parse SOA record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/soa-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(72000, record.getTtl());
        assertEquals(DomainName.of("soa.test-001."), record.getName());

        SOAResourceRecord soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("mname-001.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("rname-001.test."), soaRecord.getResponsibleName());
        assertEquals(2024033101L, soaRecord.getSerial());
        assertEquals(1800, soaRecord.getRefresh());
        assertEquals(900, soaRecord.getRetry());
        assertEquals(604800, soaRecord.getExpire());
        assertEquals(86400, soaRecord.getMinTtl());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse SOA record: <domain-name> <class> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordClass("HS");
        config.setDefaultTtl(36000);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/soa-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("HS", record.getRClass());
        assertEquals(36000, record.getTtl());
        assertEquals(DomainName.of("soa.test-002."), record.getName());

        SOAResourceRecord soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("mname-002.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("rname-002.test."), soaRecord.getResponsibleName());
        assertEquals(1022030402L, soaRecord.getSerial());
        assertEquals(60, soaRecord.getRefresh());
        assertEquals(300, soaRecord.getRetry());
        assertEquals(5002001, soaRecord.getExpire());
        assertEquals(32123, soaRecord.getMinTtl());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse SOA record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new SOAResourceRecordParser());
        config.setDefaultTtl(1299);
        config.setDefaultRClass("CS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/soa-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(1299, record.getTtl());
        assertEquals(DomainName.of("soa.test-003."), record.getName());

        SOAResourceRecord soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("mname-003.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("rname-003.test."), soaRecord.getResponsibleName());
        assertEquals(555666777L, soaRecord.getSerial());
        assertEquals(234, soaRecord.getRefresh());
        assertEquals(3700, soaRecord.getRetry());
        assertEquals(654321, soaRecord.getExpire());
        assertEquals(5223, soaRecord.getMinTtl());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse SOA record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new SOAResourceRecordParser());
        config.setDefaultRClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/soa-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(100, record.getTtl());
        assertEquals(DomainName.of("soa.test-004."), record.getName());

        SOAResourceRecord soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("mname-004.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("rname-004.test."), soaRecord.getResponsibleName());
        assertEquals(1, soaRecord.getSerial());
        assertEquals(66, soaRecord.getRefresh());
        assertEquals(890, soaRecord.getRetry());
        assertEquals(345677, soaRecord.getExpire());
        assertEquals(67000, soaRecord.getMinTtl());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse SOA record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new SOAResourceRecordParser());
        config.addRecordClass("IN");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/soa-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(6543210, record.getTtl());
        assertEquals(DomainName.of("soa.test-005."), record.getName());

        SOAResourceRecord soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("mname-005.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("rname-005.test."), soaRecord.getResponsibleName());
        assertEquals(3423512, soaRecord.getSerial());
        assertEquals(61252, soaRecord.getRefresh());
        assertEquals(2342, soaRecord.getRetry());
        assertEquals(812432, soaRecord.getExpire());
        assertEquals(34256, soaRecord.getMinTtl());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple SOA records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new SOAResourceRecordParser());
        config.setDefaultTtl(86400);
        config.setDefaultRClass("IN");
        config.addRecordClass("IN");
        config.addRecordClass("CH");
        config.addRecordClass("CS");
        config.addRecordClass("HS");

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/soa-006.txt"));

        ResourceRecord record;
        SOAResourceRecord soaRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(25232125, record.getTtl());
        assertEquals(DomainName.of("a.soa.test-006."), record.getName());
        soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("a.mname-006.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("a.rname-006.test."), soaRecord.getResponsibleName());
        assertEquals(32312552, soaRecord.getSerial());
        assertEquals(2356, soaRecord.getRefresh());
        assertEquals(112, soaRecord.getRetry());
        assertEquals(66, soaRecord.getExpire());
        assertEquals(234556, soaRecord.getMinTtl());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(25232125, record.getTtl());
        assertEquals(DomainName.of("b.soa.test-006."), record.getName());
        soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("b.mname-006.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("b.rname-006.test."), soaRecord.getResponsibleName());
        assertEquals(3423512, soaRecord.getSerial());
        assertEquals(123, soaRecord.getRefresh());
        assertEquals(6612356, soaRecord.getRetry());
        assertEquals(2312, soaRecord.getExpire());
        assertEquals(5521, soaRecord.getMinTtl());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("IN", record.getRClass());
        assertEquals(51234, record.getTtl());
        assertEquals(DomainName.of("c.soa.test-006."), record.getName());
        soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("c.mname-006.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("c.rname-006.test."), soaRecord.getResponsibleName());
        assertEquals(5531, soaRecord.getSerial());
        assertEquals(664, soaRecord.getRefresh());
        assertEquals(1251678, soaRecord.getRetry());
        assertEquals(22134, soaRecord.getExpire());
        assertEquals(221, soaRecord.getMinTtl());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("CS", record.getRClass());
        assertEquals(51234, record.getTtl());
        assertEquals(DomainName.of("d.soa.test-006."), record.getName());
        soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("d.mname-006.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("d.rname-006.test."), soaRecord.getResponsibleName());
        assertEquals(332122, soaRecord.getSerial());
        assertEquals(3357, soaRecord.getRefresh());
        assertEquals(11, soaRecord.getRetry());
        assertEquals(38852, soaRecord.getExpire());
        assertEquals(151256, soaRecord.getMinTtl());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals("SOA", record.getType());
        assertEquals("HS", record.getRClass());
        assertEquals(111, record.getTtl());
        assertEquals(DomainName.of("e.soa.test-006."), record.getName());
        soaRecord = assertInstanceOf(SOAResourceRecord.class, record);
        assertEquals(DomainName.of("e.mname-006.test."), soaRecord.getMasterName());
        assertEquals(DomainName.of("e.rname-006.test."), soaRecord.getResponsibleName());
        assertEquals(678, soaRecord.getSerial());
        assertEquals(112467, soaRecord.getRefresh());
        assertEquals(123, soaRecord.getRetry());
        assertEquals(66432, soaRecord.getExpire());
        assertEquals(8799, soaRecord.getMinTtl());

        assertFalse(records.hasNext());
        records.close();
    }
}