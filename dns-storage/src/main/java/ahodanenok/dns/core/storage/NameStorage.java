package ahodanenok.dns.core.storage;

import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.question.QRecordClass;
import ahodanenok.dns.core.zone.Zone;

public interface NameStorage {

    void importZone(Zone zone);

    List<Zone> exportZones(DomainName name, QRecordClass rclass);

    Node getRoot(DomainName name, QRecordClass rclass);

    Node findNodeExact(DomainName name, QRecordClass rclass);

    // todo: rename findNodeNearest
    Node findNearestNode(DomainName name, QRecordClass rclass);
}
