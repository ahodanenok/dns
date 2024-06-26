package ahodanenok.dns.core.storage;

import java.util.ArrayList;
import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.question.QRecordClass;
import ahodanenok.dns.core.question.StandardQRecordClass;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.zone.Zone;

public final class DefaultNameStorage implements NameStorage {

    private List<Zone> zones = new ArrayList<>();

    @Override
    public void importZone(Zone zone) {
        Zone existingZone = zones.stream()
            .filter(z -> z.getName().equals(zone.getName())
                && z.getRClass().equals(zone.getRClass()))
            .findFirst()
            .orElse(null);

        if (existingZone == null) {
            zones.add(zone);
            return;
        }

        if (existingZone.getSerial() != zone.getSerial()) {
            zones.remove(existingZone);
            zones.add(zone);
        }
    }

    @Override
    public List<Zone> exportZones(DomainName name, QRecordClass rclass) {
        return getZones(name, rclass);
    }

    @Override
    public Node getRoot(DomainName name, QRecordClass rclass) {
        List<Zone> zones = getZones(name, rclass);
        if (zones.isEmpty()) {
            return null;
        }

        List<ResourceRecord> records = new ArrayList<>();
        for (Zone zone : zones) {
            for (ResourceRecord record : zone.getRecords()) {
                if (record.getName().equals(zone.getName())) {
                    records.add(record);
                }
            }
        }

        if (!records.isEmpty()) {
            return new Node(zones.get(0).getName(), records);
        } else {
            return null;
        }
    }

    @Override
    public Node findNodeExact(DomainName name, QRecordClass rclass) {
        List<Zone> zones = getZones(name, rclass);
        if (zones.isEmpty()) {
            return null;
        }

        List<ResourceRecord> records = new ArrayList<>();
        for (Zone zone : zones) {
            for (ResourceRecord record : zone.getRecords()) {
                if (record.getName().equals(name)) {
                    records.add(record);
                }
            }
        }

        if (!records.isEmpty()) {
            return new Node(name, records);
        } else {
            return null;
        }
    }

    @Override
    public Node findNearestNode(DomainName name, QRecordClass rclass) {
        Node node = findNodeExact(name, rclass);
        if (node != null) {
            return node;
        }

        List<Zone> zones = getZones(name, rclass);
        if (zones.isEmpty()) {
            return null;
        }

        List<ResourceRecord> records = new ArrayList<>();
        for (DomainName ancestor : name.ancestors()) {
            for (Zone zone : zones) {
                for (ResourceRecord record : zone.getRecords()) {
                    if (record.getName().equals(ancestor)) {
                        records.add(record);
                    }
                }
            }

            if (!records.isEmpty()) {
                return new Node(ancestor, records);
            }
        }

        return null;
    }

    private List<Zone> getZones(DomainName name, QRecordClass rclass) {
        List<Zone> result = new ArrayList<>();
        for (Zone zone : zones) {
            if (zone.getName().equals(name)
                    && (rclass.equals(StandardQRecordClass.ANY) || zone.getRClass().equals(rclass))) {
                result.add(zone);
            }
        }

        if (!result.isEmpty()) {
            return result;
        }

        for (DomainName ancestor : name.ancestors()) {
            for (Zone zone : zones) {
                if (zone.getName().equals(ancestor)
                        && (rclass.equals(StandardQRecordClass.ANY) || zone.getRClass().equals(rclass))) {
                    result.add(zone);
                }
            }

            if (!result.isEmpty()) {
                return result;
            }
        }

        return result;
    }
}
