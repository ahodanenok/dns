package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.HInfoResourceRecord;

public final class HInfoResourceRecordParser implements ResourceRecordParser<HInfoResourceRecord> {

    public String getRecordType() {
        return "HINFO";
    }

    public HInfoResourceRecord createInitialRecord() {
        return new HInfoResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, HInfoResourceRecord target) throws Exception {
        target.setCPU(reader.readString());
        target.setOS(reader.readString());
    }
}
