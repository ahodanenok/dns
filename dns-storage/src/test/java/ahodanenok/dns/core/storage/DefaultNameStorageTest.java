package ahodanenok.dns.core.storage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.message.StandardQRecordClass;
import ahodanenok.dns.core.record.AResourceRecord;
import ahodanenok.dns.core.record.MXResourceRecord;
import ahodanenok.dns.core.record.NSResourceRecord;
import ahodanenok.dns.core.record.RecordClass;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;
import ahodanenok.dns.core.record.SOAResourceRecord;
import ahodanenok.dns.core.zone.Zone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultNameStorageTest {

    @Test
    public void testGetRootSpecificRClass() {
        Zone zoneIN = new Zone(List.of(
            createSOARecord(DomainName.of("example", "com", ""), StandardRecordClass.IN),
            createARecord(DomainName.of("example", "com", ""), StandardRecordClass.IN, "192.168.1.1"),
            createNSRecord(DomainName.of("example", "com", ""), StandardRecordClass.IN, DomainName.of("ns", "example", "com", "")),
            createARecord(DomainName.of("address", "example", "com", ""), StandardRecordClass.IN, "192.168.1.2")
        ));
        Zone zoneCH = new Zone(List.of(
            createSOARecord(DomainName.of("example", "com", ""), StandardRecordClass.CH),
            createARecord(DomainName.of("example", "com", ""), StandardRecordClass.CH, "1.1.1.1")
        ));

        NameStorage storage = new DefaultNameStorage();
        storage.importZone(zoneIN);
        storage.importZone(zoneCH);

        Node root = storage.getRoot(DomainName.of("address", "example", "com", ""), StandardRecordClass.IN);
        assertNotNull(root);
        assertEquals(DomainName.of("example", "com", ""), root.getName());
        assertEquals(3, root.getRecords().size());
        assertEquals(Set.of(DomainName.of("example", "com", "")),
            root.getRecords().stream().map(r -> r.getName()).collect(Collectors.toSet()));
        assertNotNull(root.getRecords().stream()
            .filter(r -> r.getType().equals(StandardRecordType.SOA)).findFirst().orElse(null));
        assertEquals(List.of("192.168.1.1"),
            root.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.A))
                .map(r -> ((AResourceRecord) r).getAddress())
                .collect(Collectors.toList()));
        assertEquals(List.of(DomainName.of("ns", "example", "com", "")),
            root.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.NS))
                .map(r -> ((NSResourceRecord) r).getNSName())
                .collect(Collectors.toList()));
    }

    @Test
    public void testGetRootAnyRClass() {
        Zone zoneIN = new Zone(List.of(
            createSOARecord(DomainName.of("example", "com", ""), StandardRecordClass.IN),
            createARecord(DomainName.of("example", "com", ""), StandardRecordClass.IN, "192.168.1.1"),
            createNSRecord(DomainName.of("example", "com", ""), StandardRecordClass.IN, DomainName.of("ns", "example", "com", "")),
            createARecord(DomainName.of("address", "example", "com", ""), StandardRecordClass.IN, "192.168.1.2")
        ));
        Zone zoneCH = new Zone(List.of(
            createSOARecord(DomainName.of("example", "com", ""), StandardRecordClass.CH),
            createARecord(DomainName.of("example", "com", ""), StandardRecordClass.CH, "1.1.1.1")
        ));

        NameStorage storage = new DefaultNameStorage();
        storage.importZone(zoneIN);
        storage.importZone(zoneCH);

        Node root = storage.getRoot(DomainName.of("address", "example", "com", ""), StandardQRecordClass.ANY);
        assertNotNull(root);
        assertEquals(DomainName.of("example", "com", ""), root.getName());
        assertEquals(5, root.getRecords().size());
        assertEquals(Set.of(DomainName.of("example", "com", "")),
            root.getRecords().stream().map(r -> r.getName()).collect(Collectors.toSet()));
        assertEquals(2, root.getRecords().stream().filter(r -> r.getType().equals(StandardRecordType.SOA)).count());
        assertEquals(Set.of("192.168.1.1", "1.1.1.1"),
            root.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.A))
                .map(r -> ((AResourceRecord) r).getAddress())
                .collect(Collectors.toSet()));
        assertEquals(List.of(DomainName.of("ns", "example", "com", "")),
            root.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.NS))
                .map(r -> ((NSResourceRecord) r).getNSName())
                .collect(Collectors.toList()));
    }

    @Test
    public void testFindNodeExactSpecificRClass() {
        Zone zoneIN = new Zone(List.of(
            createSOARecord(DomainName.of("test", "com", ""), StandardRecordClass.IN),
            createARecord(DomainName.of("srv-1", "b", "test", "com", ""), StandardRecordClass.IN, "1.1.1.100"),
            createARecord(DomainName.of("srv-1", "b", "test", "com", ""), StandardRecordClass.IN, "1.1.1.200"),
            createMXRecord(DomainName.of("srv-1", "b", "test", "com", ""), StandardRecordClass.IN, DomainName.of("mail-aaa", "test", "com")),
            createARecord(DomainName.of("srv-2", "b", "test", "com", ""), StandardRecordClass.IN, "2.2.2.100"),
            createARecord(DomainName.of("srv-2", "b", "test", "com", ""), StandardRecordClass.IN, "2.2.2.200")
        ));
        Zone zoneHS = new Zone(List.of(
            createSOARecord(DomainName.of("test", "com", ""), StandardRecordClass.HS),
            createARecord(DomainName.of("srv-1", "b", "test", "com", ""), StandardRecordClass.HS, "33.33.33.33")
        ));

        NameStorage storage = new DefaultNameStorage();
        storage.importZone(zoneIN);
        storage.importZone(zoneHS);

        AResourceRecord aRecord;
        MXResourceRecord mxRecord;

        Node node = storage.findNodeExact(DomainName.of("srv-1", "b", "test", "com", ""), StandardRecordClass.IN);
        assertNotNull(node);
        assertEquals(DomainName.of("srv-1", "b", "test", "com", ""), node.getName());
        assertEquals(3, node.getRecords().size());
        assertEquals(Set.of(DomainName.of("srv-1", "b", "test", "com", "")),
            node.getRecords().stream().map(r -> r.getName()).collect(Collectors.toSet()));
        assertEquals(Set.of(StandardRecordClass.IN),
            node.getRecords().stream().map(r -> r.getRClass()).collect(Collectors.toSet()));
        assertEquals(Set.of("1.1.1.100", "1.1.1.200"),
            node.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.A))
                .map(r -> ((AResourceRecord) r).getAddress())
                .collect(Collectors.toSet()));
        assertEquals(List.of(DomainName.of("mail-aaa", "test", "com")),
            node.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.MX))
                .map(r -> ((MXResourceRecord) r).getExchange())
                .collect(Collectors.toList()));
    }

    @Test
    public void testFindNodeExactAnyRClass() {
        Zone zoneIN = new Zone(List.of(
            createSOARecord(DomainName.of("service", "com", ""), StandardRecordClass.IN),
            createARecord(DomainName.of("aaa", "service", "com", ""), StandardRecordClass.IN, "1.1.1.100"),
            createARecord(DomainName.of("aaa", "service", "com", ""), StandardRecordClass.IN, "1.1.1.200"),
            createMXRecord(DomainName.of("aaa", "service", "com", ""), StandardRecordClass.IN, DomainName.of("mail-IN", "test", "com", "")),
            createARecord(DomainName.of("bbb", "service", "com", ""), StandardRecordClass.IN, "2.2.2.100")
        ));
        Zone zoneCH = new Zone(List.of(
            createSOARecord(DomainName.of("service", "com", ""), StandardRecordClass.CH),
            createARecord(DomainName.of("aaa", "service", "com", ""), StandardRecordClass.CH, "10.10.10.10"),
            createARecord(DomainName.of("bbb", "service", "com", ""), StandardRecordClass.CH, "11.11.11.11")
        ));
        Zone zoneHS = new Zone(List.of(
            createSOARecord(DomainName.of("service", "com", ""), StandardRecordClass.HS),
            createMXRecord(DomainName.of("aaa", "service", "com", ""), StandardRecordClass.HS, DomainName.of("mail-HS", "test", "com", "")),
            createARecord(DomainName.of("bbb", "service", "com", ""), StandardRecordClass.HS, "22.22.22.22")
        ));

        NameStorage storage = new DefaultNameStorage();
        storage.importZone(zoneIN);
        storage.importZone(zoneCH);
        storage.importZone(zoneHS);

        Node node = storage.findNodeExact(DomainName.of("aaa", "service", "com", ""), StandardQRecordClass.ANY);
        assertNotNull(node);
        assertEquals(DomainName.of("aaa", "service", "com", ""), node.getName());
        assertEquals(5, node.getRecords().size());
        assertEquals(Set.of(DomainName.of("aaa", "service", "com", "")),
            node.getRecords().stream().map(r -> r.getName()).collect(Collectors.toSet()));
        assertEquals(Set.of(StandardRecordClass.IN, StandardRecordClass.HS, StandardRecordClass.CH),
            node.getRecords().stream().map(r -> r.getRClass()).collect(Collectors.toSet()));
        assertEquals(Set.of("1.1.1.100", "1.1.1.200", "10.10.10.10"),
            node.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.A))
                .map(r -> ((AResourceRecord) r).getAddress())
                .collect(Collectors.toSet()));
        assertEquals(Set.of(DomainName.of("mail-IN", "test", "com", ""), DomainName.of("mail-HS", "test", "com", "")),
            node.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.MX))
                .map(r -> ((MXResourceRecord) r).getExchange())
                .collect(Collectors.toSet()));
    }

    @Test
    public void testFindNodeNearestSpecificRClass() {
        Zone zoneIN = new Zone(List.of(
            createSOARecord(DomainName.of("com", ""), StandardRecordClass.IN),
            createARecord(DomainName.of("test", "com", ""), StandardRecordClass.IN, "1.1.1.234"),
            createMXRecord(DomainName.of("test", "com", ""), StandardRecordClass.IN, DomainName.of("mail", "test", "com")),
            createARecord(DomainName.of("data", "com", ""), StandardRecordClass.IN, "2.2.2.100")
        ));
        Zone zoneHS = new Zone(List.of(
            createSOARecord(DomainName.of("test", "com", ""), StandardRecordClass.HS),
            createARecord(DomainName.of("b", "test", "com", ""), StandardRecordClass.HS, "33.33.33.33")
        ));

        NameStorage storage = new DefaultNameStorage();
        storage.importZone(zoneIN);
        storage.importZone(zoneHS);

        Node node = storage.findNearestNode(DomainName.of("a", "b", "c", "test", "com", ""), StandardRecordClass.IN);
        assertNotNull(node);
        assertEquals(DomainName.of("test", "com", ""), node.getName());
        assertEquals(2, node.getRecords().size());
        assertEquals(Set.of(DomainName.of("test", "com", "")),
            node.getRecords().stream().map(r -> r.getName()).collect(Collectors.toSet()));
        assertEquals(Set.of(StandardRecordClass.IN),
            node.getRecords().stream().map(r -> r.getRClass()).collect(Collectors.toSet()));
        assertEquals(List.of("1.1.1.234"),
            node.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.A))
                .map(r -> ((AResourceRecord) r).getAddress())
                .collect(Collectors.toList()));
        assertEquals(List.of(DomainName.of("mail", "test", "com")),
            node.getRecords().stream()
                .filter(r -> r.getType().equals(StandardRecordType.MX))
                .map(r -> ((MXResourceRecord) r).getExchange())
                .collect(Collectors.toList()));
    }

    private ResourceRecord createSOARecord(DomainName name, RecordClass rclass) {
        SOAResourceRecord record = new SOAResourceRecord();
        record.setName(name);
        record.setTtl(3600);
        record.setRClass(rclass);
        record.setMasterName(DomainName.of("mname", "test"));
        record.setResponsibleName(DomainName.of("rname", "test"));
        record.setSerial(1);
        record.setRefresh(60);
        record.setRetry(30);
        record.setExpire(100);
        record.setMinTtl(10);

        return record;
    }

    private ResourceRecord createARecord(DomainName name, RecordClass rclass, String address) {
        AResourceRecord record = new AResourceRecord();
        record.setName(name);
        record.setTtl(3600);
        record.setRClass(rclass);
        record.setAddress(address);

        return record;
    }

    private ResourceRecord createNSRecord(DomainName name, RecordClass rclass, DomainName nsName) {
        NSResourceRecord record = new NSResourceRecord();
        record.setName(name);
        record.setTtl(3600);
        record.setRClass(rclass);
        record.setNSName(nsName);

        return record;
    }

    private ResourceRecord createMXRecord(DomainName name, RecordClass rclass, DomainName exchange) {
        MXResourceRecord record = new MXResourceRecord();
        record.setName(name);
        record.setTtl(3600);
        record.setRClass(rclass);
        record.setPreference(1 + (int) Math.floor(Math.random()) * 10);
        record.setExchange(exchange);

        return record;
    }
}
