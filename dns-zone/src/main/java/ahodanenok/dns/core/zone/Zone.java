package ahodanenok.dns.core.zone;

import java.util.Collections;
import java.util.List;

import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.SOAResourceRecord;

public final class Zone {

    private final SOAResourceRecord soaRecord;
    private final List<ResourceRecord> records;

    public Zone(List<ResourceRecord> records) {
        this.soaRecord = records.stream()
            .filter(r -> "SOA".equals(r.getType()))
            .findFirst()
            .map(r -> (SOAResourceRecord) r)
            .orElseThrow(() -> new IllegalArgumentException("No SOA record found"));
        this.records = Collections.unmodifiableList(records);
    }

    public DomainName getName() {
        return soaRecord.getName();
    }

    public List<ResourceRecord> getRecords() {
        return records;
    }
}
