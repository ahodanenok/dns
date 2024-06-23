package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.MXResourceRecord;

public final class MXResourceRecordParser implements ResourceRecordParser<MXResourceRecord> {

    public String getRecordType() {
        return "MX";
    }

    public MXResourceRecord createInitialRecord() {
        return new MXResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, MXResourceRecord target) throws Exception {
        target.setPreference(ParseUtils.parseInt(reader));
        target.setExchange(ParseUtils.parseDomainName(reader));
    }
}
