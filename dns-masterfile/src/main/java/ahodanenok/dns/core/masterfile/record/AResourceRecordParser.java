package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.AResourceRecord;

public final class AResourceRecordParser implements ResourceRecordParser<AResourceRecord> {

    public String getRecordType() {
        return "A";
    }

    public AResourceRecord createInitialRecord() {
        return new AResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, AResourceRecord target) throws Exception {
        target.setAddress(reader.readString());
    }
}
