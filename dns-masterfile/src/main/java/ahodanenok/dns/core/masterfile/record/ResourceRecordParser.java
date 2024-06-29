package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;

public interface ResourceRecordParser<T extends ResourceRecord> {

    RecordType getRecordType();

    T createInitialRecord();

    void parseRData(MasterFileDataReader reader, T record) throws Exception;
}
