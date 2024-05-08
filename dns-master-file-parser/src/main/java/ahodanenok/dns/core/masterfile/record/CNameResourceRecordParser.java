package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.model.CNameResourceRecord;
import ahodanenok.dns.core.model.DomainName;

public final class CNameResourceRecordParser implements ResourceRecordParser<CNameResourceRecord> {

    public String getRecordType() {
        return "CNAME";
    }

    public CNameResourceRecord createInitialRecord() {
        return new CNameResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, CNameResourceRecord target) throws Exception {
        target.setCanonicalName(DomainName.of(reader.readString()));
    }
}
