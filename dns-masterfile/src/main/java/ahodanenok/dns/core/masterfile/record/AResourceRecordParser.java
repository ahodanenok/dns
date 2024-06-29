package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.AResourceRecord;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.StandardRecordType;

public final class AResourceRecordParser implements ResourceRecordParser<AResourceRecord> {

    public RecordType getRecordType() {
        return StandardRecordType.A;
    }

    public AResourceRecord createInitialRecord() {
        return new AResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, AResourceRecord target) throws Exception {
        target.setAddress(reader.readString());
    }
}
