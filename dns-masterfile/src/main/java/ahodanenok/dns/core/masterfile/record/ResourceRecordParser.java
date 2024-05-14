package ahodanenok.dns.core.masterfile.record;

import ahodanenok.dns.core.masterfile.MasterFileDataReader;
import ahodanenok.dns.core.model.ResourceRecord;

public interface ResourceRecordParser<T extends ResourceRecord> {

    String getRecordType();

    T createInitialRecord();

    void parseRData(MasterFileDataReader reader, T record) throws Exception;
}
