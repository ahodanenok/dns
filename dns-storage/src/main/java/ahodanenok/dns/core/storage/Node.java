package ahodanenok.dns.core.storage;

import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.ResourceRecord;

public final class Node {

    private final DomainName name;
    private final List<ResourceRecord> records;

    public Node(DomainName name, List<ResourceRecord> records) {
        this.name = name;
        this.records = records;
    }

    public DomainName getName() {
        return name;
    }

    public List<ResourceRecord> getRecords() {
        return records;
    }
}
