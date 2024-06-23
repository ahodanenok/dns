package ahodanenok.dns.core.masterfile;

import java.util.Iterator;

import ahodanenok.dns.core.record.ResourceRecord;

public interface ResourceRecordParseIterator extends Iterator<ResourceRecord>, Iterable<ResourceRecord>, AutoCloseable {

    default Iterator<ResourceRecord> iterator() {
        return this;
    }
}
