package ahodanenok.dns.core.masterfile;

import java.util.Map;
import java.util.HashMap;

import ahodanenok.dns.core.masterfile.record.ResourceRecordParser;
import ahodanenok.dns.core.model.ResourceRecord;

public class MasterFileParserConfiguration {

    final Map<String, ResourceRecordParser<? extends ResourceRecord>> recordParsers = new HashMap<>();

    public final void addRecordParser(ResourceRecordParser<? extends ResourceRecord> parser) {
        recordParsers.put(parser.getRecordType(), parser);
    }
}
