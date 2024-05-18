package ahodanenok.dns.core.masterfile;

import java.util.Iterator;

import ahodanenok.dns.core.model.ResourceRecord;

public interface ResourceRecordParseIterator extends Iterator<ResourceRecord>, Iterable<ResourceRecord>, AutoCloseable {

    default Iterator<ResourceRecord> iterator() {
        return this;
    }
}
