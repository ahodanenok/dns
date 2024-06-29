package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.StandardRecordType;

public final class CNameResourceRecordParser implements ResourceRecordParser<CNameResourceRecord> {

    public RecordType getRecordType() {
        return StandardRecordType.CNAME;
    }

    public CNameResourceRecord createInitialRecord() {
        return new CNameResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, CNameResourceRecord target) throws Exception {
        target.setCanonicalName(ParseUtils.parseDomainName(reader));
    }
}
