package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.StandardRecordType;
import ahodanenok.dns.core.record.SOAResourceRecord;

public final class SOAResourceRecordParser implements ResourceRecordParser<SOAResourceRecord> {

    public RecordType getRecordType() {
        return StandardRecordType.SOA;
    }

    public SOAResourceRecord createInitialRecord() {
        return new SOAResourceRecord();
    }

    public void parseRData(MasterFileDataReader reader, SOAResourceRecord target) throws Exception {
        target.setMasterName(ParseUtils.parseDomainName(reader));
        target.setResponsibleName(ParseUtils.parseDomainName(reader));
        target.setSerial(ParseUtils.parseUnsignedInt(reader));
        target.setRefresh(ParseUtils.parseInt(reader));
        target.setRetry(ParseUtils.parseInt(reader));
        target.setExpire(ParseUtils.parseInt(reader));
        target.setMinTtl(ParseUtils.parseUnsignedInt(reader));
    }
}
