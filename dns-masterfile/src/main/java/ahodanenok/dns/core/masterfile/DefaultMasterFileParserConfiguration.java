package ahodanenok.dns.core.masterfile;

import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ahodanenok.dns.core.masterfile.record.AResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.CNameResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.HInfoResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.MXResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.NSResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.PTRResourceRecordParser;
import ahodanenok.dns.core.masterfile.record.SOAResourceRecordParser;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;

public class DefaultMasterFileParserConfiguration extends MasterFileParserConfiguration{

    public DefaultMasterFileParserConfiguration() {
        setDefaultRClass(StandardRecordClass.IN);
        setDefaultTtl(86400);
        addRecordClass(StandardRecordClass.IN);
        addRecordClass(StandardRecordClass.CS);
        addRecordClass(StandardRecordClass.HS);
        addRecordClass(StandardRecordClass.CH);
        addRecordParser(new CNameResourceRecordParser());
        addRecordParser(new HInfoResourceRecordParser());
        addRecordParser(new MXResourceRecordParser());
        addRecordParser(new NSResourceRecordParser());
        addRecordParser(new PTRResourceRecordParser());
        addRecordParser(new SOAResourceRecordParser());
        addRecordParser(new AResourceRecordParser());
    }
}
