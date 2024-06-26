package ahodanenok.dns.core.masterfile;

import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ahodanenok.dns.core.masterfile.record.ResourceRecordParser;
import ahodanenok.dns.core.record.RecordClass;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;

public class MasterFileParserConfiguration {

    int defaultTtl;
    RecordClass defaultRClass;
    final Set<RecordClass> recordClasses = new LinkedHashSet<>();
    final Map<RecordType, ResourceRecordParser<? extends ResourceRecord>> recordParsers = new HashMap<>();

    public final void setDefaultRClass(RecordClass rclass) {
        this.defaultRClass = rclass;
    }

    public final void setDefaultTtl(int ttl) {
        this.defaultTtl = ttl;
    }

    public final void addRecordClass(RecordClass rclass) {
        this.recordClasses.add(rclass);
    }

    public final void addRecordParser(ResourceRecordParser<? extends ResourceRecord> parser) {
        recordParsers.put(parser.getRecordType(), parser);
    }
}
