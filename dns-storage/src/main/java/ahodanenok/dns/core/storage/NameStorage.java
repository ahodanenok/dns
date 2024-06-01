package ahodanenok.dns.core.storage;

import java.util.List;

import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.Zone;

public interface NameStorage {

    void importZone(Zone zone);

    List<Zone> exportZones(DomainName name, String rclass);

    Node getRoot(DomainName name, String rclass);

    Node findNodeExact(DomainName name, String rclass);

    // todo: rename findNodeNearest
    Node findNearestNode(DomainName name, String rclass);
}
