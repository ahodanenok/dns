package ahodanenok.dns.core.model;

import java.util.Collections;
import java.util.List;

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

    public String getRClass() {
        return soaRecord.getRClass();
    }

    public long getSerial() {
        return soaRecord.getSerial();
    }

    public List<ResourceRecord> getRecords() {
        return records;
    }
}
