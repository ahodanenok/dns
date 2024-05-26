package ahodanenok.dns.core.storage;

import ahodanenok.dns.core.model.DomainName;

public interface NameStorage {

    Node findNearestNode(DomainName name, String rclass);
}
