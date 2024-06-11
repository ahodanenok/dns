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
import ahodanenok.dns.core.model.ResourceRecord;

public class DefaultMasterFileParserConfiguration extends MasterFileParserConfiguration{

    public DefaultMasterFileParserConfiguration() {
        setDefaultRClass("IN");
        setDefaultTtl(86400);
        addRecordClass("IN");
        addRecordClass("CS");
        addRecordClass("HS");
        addRecordClass("CH");
        addRecordParser(new CNameResourceRecordParser());
        addRecordParser(new HInfoResourceRecordParser());
        addRecordParser(new MXResourceRecordParser());
        addRecordParser(new NSResourceRecordParser());
        addRecordParser(new PTRResourceRecordParser());
        addRecordParser(new SOAResourceRecordParser());
        addRecordParser(new AResourceRecordParser());
    }
}
