package ahodanenok.dns.core.zone;

import java.util.Collections;
import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.RecordClass;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordType;
import ahodanenok.dns.core.record.SOAResourceRecord;

public final class Zone {

    private final SOAResourceRecord soaRecord;
    private final List<ResourceRecord> records;

    public Zone(List<ResourceRecord> records) {
        this.soaRecord = records.stream()
            .filter(r -> StandardRecordType.SOA.equals(r.getType()))
            .findFirst()
            .map(r -> (SOAResourceRecord) r)
            .orElseThrow(() -> new IllegalArgumentException("No SOA record found"));
        this.records = Collections.unmodifiableList(records);
    }

    public DomainName getName() {
        return soaRecord.getName();
    }

    public RecordClass getRClass() {
        return soaRecord.getRClass();
    }

    public long getSerial() {
        return soaRecord.getSerial();
    }

    public List<ResourceRecord> getRecords() {
        return records;
    }
}
