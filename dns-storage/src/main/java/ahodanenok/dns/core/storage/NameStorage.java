package ahodanenok.dns.core.storage;

import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.zone.Zone;

public interface NameStorage {

    void importZone(Zone zone);

    List<Zone> exportZones(DomainName name, String rclass);

    Node getRoot(DomainName name, String rclass);

    Node findNodeExact(DomainName name, String rclass);

    // todo: rename findNodeNearest
    Node findNearestNode(DomainName name, String rclass);
}
