package ahodanenok.dns.core.masterfile.record;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.masterfile.FileUtils;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.ResourceRecordParseIterator;
import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.MXResourceRecord;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;

import static org.junit.jupiter.api.Assertions.*;

public class MXResourceRecordParserTest {

    @Test
    @DisplayName("Parse MX record: <domain-name> <TTL> <class> <type> <RDATA>")
    public void test_001() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new MXResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/mx-001.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(7201, record.getTtl());
        assertEquals(DomainName.parse("test-001.email."), record.getName());

        MXResourceRecord mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(10, mxRecord.getPreference());
        assertEquals(DomainName.parse("admin"), mxRecord.getExchange());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse MX record: <domain-name> <MX> <type> <RDATA>")
    public void test_002() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new MXResourceRecordParser());
        config.addRecordClass(StandardRecordClass.CS);
        config.setDefaultTtl(56789);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/mx-002.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(56789, record.getTtl());
        assertEquals(DomainName.parse("test-002.email."), record.getName());

        MXResourceRecord mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(523, mxRecord.getPreference());
        assertEquals(DomainName.parse("abc.test-001.email."), mxRecord.getExchange());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse MX record: <domain-name> <type> <RDATA>")
    public void test_003() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new MXResourceRecordParser());
        config.setDefaultTtl(300);
        config.setDefaultRClass(StandardRecordClass.CH);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/mx-003.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(300, record.getTtl());
        assertEquals(DomainName.parse("test-003.email."), record.getName());

        MXResourceRecord mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(3, mxRecord.getPreference());
        assertEquals(DomainName.parse("one.two.three."), mxRecord.getExchange());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse MX record: <domain-name> <TTL> <type> <RDATA>")
    public void test_004() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new MXResourceRecordParser());
        config.setDefaultRClass(StandardRecordClass.HS);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/mx-004.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.HS, record.getRClass());
        assertEquals(222, record.getTtl());
        assertEquals(DomainName.parse("test-004.email."), record.getName());

        MXResourceRecord mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(1234, mxRecord.getPreference());
        assertEquals(DomainName.parse("user.test.exchange."), mxRecord.getExchange());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse MX record: <domain-name> <class> <TTL> <type> <RDATA>")
    public void test_005() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new MXResourceRecordParser());
        config.addRecordClass(StandardRecordClass.IN);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/mx-005.txt"));
        assertTrue(records.hasNext());

        ResourceRecord record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(300000, record.getTtl());
        assertEquals(DomainName.parse("test-005.email."), record.getName());

        MXResourceRecord mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(5, mxRecord.getPreference());
        assertEquals(DomainName.parse("srv.test."), mxRecord.getExchange());

        assertFalse(records.hasNext());
        records.close();
    }

    @Test
    @DisplayName("Parse multiple MX records")
    public void test_006() throws Exception {
        MasterFileParserConfiguration config = new MasterFileParserConfiguration();
        config.addRecordParser(new MXResourceRecordParser());
        config.setDefaultTtl(3600);
        config.setDefaultRClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.IN);
        config.addRecordClass(StandardRecordClass.CH);
        config.addRecordClass(StandardRecordClass.CS);
        config.addRecordClass(StandardRecordClass.HS);

        MasterFileParser parser = new MasterFileParser(config);
        ResourceRecordParseIterator records = parser.parse(FileUtils.getTestFilePath("record/mx-006.txt"));

        ResourceRecord record;
        MXResourceRecord mxRecord;

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.CH, record.getRClass());
        assertEquals(3600, record.getTtl());
        assertEquals(DomainName.parse("test-006-1.email."), record.getName());
        mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(300, mxRecord.getPreference());
        assertEquals(DomainName.parse("exch-1.test."), mxRecord.getExchange());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(532124, record.getTtl());
        assertEquals(DomainName.parse("test-006-2.email."), record.getName());
        mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(1, mxRecord.getPreference());
        assertEquals(DomainName.parse("exch-2.test."), mxRecord.getExchange());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.IN, record.getRClass());
        assertEquals(532124, record.getTtl());
        assertEquals(DomainName.parse("test-006-3.email."), record.getName());
        mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(25234, mxRecord.getPreference());
        assertEquals(DomainName.parse("exch-3.test."), mxRecord.getExchange());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(720, record.getTtl());
        assertEquals(DomainName.parse("test-006-4.email."), record.getName());
        mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(344, mxRecord.getPreference());
        assertEquals(DomainName.parse("exch-4.test."), mxRecord.getExchange());

        assertTrue(records.hasNext());
        record = records.next();
        assertEquals(StandardRecordType.MX, record.getType());
        assertEquals(StandardRecordClass.CS, record.getRClass());
        assertEquals(720, record.getTtl());
        assertEquals(DomainName.parse("test-006-5.email."), record.getName());
        mxRecord = assertInstanceOf(MXResourceRecord.class, record);
        assertEquals(62, mxRecord.getPreference());
        assertEquals(DomainName.parse("exch-5.test."), mxRecord.getExchange());

        assertFalse(records.hasNext());
        records.close();
    }
}
