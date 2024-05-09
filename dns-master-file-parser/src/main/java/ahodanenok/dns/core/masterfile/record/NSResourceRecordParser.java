package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.model.NSResourceRecord;
import ahodanenok.dns.core.model.DomainName;

public final class NSResourceRecordParser implements ResourceRecordParser<NSResourceRecord> {

    public String getRecordType() {
        return "NS";
    }

    public NSResourceRecord createInitialRecord() {
        return new NSResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, NSResourceRecord target) throws Exception {
        target.setNSName(DomainName.of(reader.readString()));
    }
}
