package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.PTRResourceRecord;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.StandardRecordType;

public final class PTRResourceRecordParser implements ResourceRecordParser<PTRResourceRecord> {

    public RecordType getRecordType() {
        return StandardRecordType.PTR;
    }

    public PTRResourceRecord createInitialRecord() {
        return new PTRResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, PTRResourceRecord target) throws Exception {
        target.setTargetName(ParseUtils.parseDomainName(reader));
    }
}
