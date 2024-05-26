package ahodanenok.dns.core.storage;

import java.util.List;

import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.ResourceRecord;

public final class Node {

    private final DomainName name;
    private final boolean leaf;
    private final List<ResourceRecord> records;

    public Node(DomainName name, boolean leaf, List<ResourceRecord> records) {
        this.name = name;
        this.leaf = leaf;
        this.records = records;
    }

    public DomainName getName() {
        return name;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public List<ResourceRecord> getRecords() {
        return records;
    }
}
