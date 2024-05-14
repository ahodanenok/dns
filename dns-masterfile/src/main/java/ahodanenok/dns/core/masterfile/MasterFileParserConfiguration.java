package ahodanenok.dns.core.masterfile;

import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ahodanenok.dns.core.masterfile.record.ResourceRecordParser;
import ahodanenok.dns.core.model.ResourceRecord;

public class MasterFileParserConfiguration {

    int defaultTtl;
    String defaultRClass;
    final Set<String> recordClasses = new LinkedHashSet<>();
    final Map<String, ResourceRecordParser<? extends ResourceRecord>> recordParsers = new HashMap<>();

    public final void setDefaultRClass(String rclass) {
        this.defaultRClass = rclass;
    }

    public final void setDefaultTtl(int ttl) {
        this.defaultTtl = ttl;
    }

    public final void addRecordClass(String rclass) {
        this.recordClasses.add(rclass);
    }

    public final void addRecordParser(ResourceRecordParser<? extends ResourceRecord> parser) {
        recordParsers.put(parser.getRecordType(), parser);
    }
}
