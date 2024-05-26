package ahodanenok.dns.core.storage;

import ahodanenok.dns.core.model.DomainName;

public interface NameStorage {

    Node findNodeExact(DomainName name, String rclass);

    // todo: rename findNodeNearestFromRoot
    Node findNearestNode(DomainName name, String rclass);
}
