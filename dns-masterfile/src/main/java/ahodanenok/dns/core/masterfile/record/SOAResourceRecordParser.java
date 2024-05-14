package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.masterfile.ParseUtils;
import ahodanenok.dns.core.model.SOAResourceRecord;

public final class SOAResourceRecordParser implements ResourceRecordParser<SOAResourceRecord> {

    public String getRecordType() {
        return "SOA";
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
