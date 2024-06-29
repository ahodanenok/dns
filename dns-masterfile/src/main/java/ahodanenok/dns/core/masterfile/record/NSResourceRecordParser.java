package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.NSResourceRecord;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.StandardRecordType;

public final class NSResourceRecordParser implements ResourceRecordParser<NSResourceRecord> {

    public RecordType getRecordType() {
        return StandardRecordType.NS;
    }

    public NSResourceRecord createInitialRecord() {
        return new NSResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, NSResourceRecord target) throws Exception {
        target.setNSName(ParseUtils.parseDomainName(reader));
    }
}
