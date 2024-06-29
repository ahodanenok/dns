package ahodanenok.dns.core.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.message.Question;
import ahodanenok.dns.core.record.AResourceRecord;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.HInfoResourceRecord;
import ahodanenok.dns.core.record.MXResourceRecord;
import ahodanenok.dns.core.record.NSResourceRecord;
import ahodanenok.dns.core.record.StandardRecordClass;
import ahodanenok.dns.core.record.StandardRecordType;
import ahodanenok.dns.core.service.query.Query;
import ahodanenok.dns.core.service.query.QueryResponse;
import ahodanenok.dns.core.storage.NameStorage;
import ahodanenok.dns.core.storage.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocalNameServiceTest {

    @Test
    public void testExactMatchOneRR() {
        AResourceRecord r1 = new AResourceRecord();
        MXResourceRecord r2 = new MXResourceRecord();
        HInfoResourceRecord r3 = new HInfoResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("db.cluster."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("db.cluster."), List.of(r1, r2, r3)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("db.cluster."), StandardRecordType.A, StandardRecordClass.IN)));
        assertEquals(1, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
    }

    @Test
    public void testExactMatchMultipleRRs() {
        AResourceRecord r1 = new AResourceRecord();
        MXResourceRecord r2 = new MXResourceRecord();
        HInfoResourceRecord r3 = new HInfoResourceRecord();
        AResourceRecord r4 = new AResourceRecord();
        MXResourceRecord r5 = new MXResourceRecord();
        MXResourceRecord r6 = new MXResourceRecord();
        AResourceRecord r7 = new AResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("user.mailbox."), StandardRecordClass.CS)).thenReturn(
            new Node(DomainName.parse("user.mailbox."), List.of(r1, r2, r3, r4, r5, r6, r7)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("user.mailbox."), StandardRecordType.MX, StandardRecordClass.CS)));
        assertEquals(3, response.getAnswer().size());
        assertSame(r2, response.getAnswer().get(0));
        assertSame(r5, response.getAnswer().get(1));
        assertSame(r6, response.getAnswer().get(2));
    }

    @Test
    public void testExactMatchCName() {
        CNameResourceRecord r1 = new CNameResourceRecord();
        r1.setCanonicalName(DomainName.parse("world."));

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("hello."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("hello."), List.of(r1)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("hello."), StandardRecordType.CNAME, StandardRecordClass.IN)));
        assertEquals(1, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
    }

    @Test
    public void testFollowOneCName() {
        CNameResourceRecord r1 = new CNameResourceRecord();
        r1.setCanonicalName(DomainName.parse("static.data."));
        AResourceRecord r2 = new AResourceRecord();
        MXResourceRecord r3 = new MXResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("images."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("images."), List.of(r1)));
        when(storage.findNearestNode(DomainName.parse("static.data."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("static.data."), List.of(r2, r3)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("images."), StandardRecordType.A, StandardRecordClass.IN)));
        assertEquals(2, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
        assertSame(r2, response.getAnswer().get(1));
    }

    @Test
    public void testFollowMultipleCNames() {
        CNameResourceRecord r1 = new CNameResourceRecord();
        r1.setCanonicalName(DomainName.parse("one."));
        CNameResourceRecord r2 = new CNameResourceRecord();
        r2.setCanonicalName(DomainName.parse("two."));
        CNameResourceRecord r3 = new CNameResourceRecord();
        r3.setCanonicalName(DomainName.parse("three."));
        CNameResourceRecord r4 = new CNameResourceRecord();
        r4.setCanonicalName(DomainName.parse("four."));
        CNameResourceRecord r5 = new CNameResourceRecord();
        r5.setCanonicalName(DomainName.parse("five."));
        AResourceRecord r6 = new AResourceRecord();
        MXResourceRecord r7 = new MXResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("initial."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("initial."), List.of(r1)));
        when(storage.findNearestNode(DomainName.parse("one."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("one."), List.of(r2)));
        when(storage.findNearestNode(DomainName.parse("two."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("two."), List.of(r3)));
        when(storage.findNearestNode(DomainName.parse("three."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("three."), List.of(r4)));
        when(storage.findNearestNode(DomainName.parse("four."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("four."), List.of(r5)));
        when(storage.findNearestNode(DomainName.parse("five."), StandardRecordClass.IN)).thenReturn(
            new Node(DomainName.parse("five."), List.of(r6, r7)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("initial."), StandardRecordType.MX, StandardRecordClass.IN)));
        assertEquals(6, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
        assertSame(r2, response.getAnswer().get(1));
        assertSame(r3, response.getAnswer().get(2));
        assertSame(r4, response.getAnswer().get(3));
        assertSame(r5, response.getAnswer().get(4));
        assertSame(r7, response.getAnswer().get(5));
    }

    @Test
    public void testReferralOneNSWithNoGlueData() {
        NSResourceRecord r1 = new NSResourceRecord();
        r1.setNSName(DomainName.parse("A.ISI.EDU."));

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("ACC.EDU."), StandardRecordClass.IN))
            .thenReturn(new Node(DomainName.parse("EDU."), List.of(r1)));
        when(storage.findNodeExact(DomainName.parse("A.ISI.EDU."), StandardRecordClass.IN))
            .thenReturn(null);

        NameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("ACC.EDU."), StandardRecordType.MX, StandardRecordClass.IN)));
        assertEquals(0, response.getAnswer().size());
        assertEquals(1, response.getAuthority().size());
        assertSame(r1, response.getAuthority().get(0));
        assertEquals(0, response.getAdditional().size());
    }

    @Test
    public void testReferralOneNSWithGlueData() {
        NSResourceRecord r1 = new NSResourceRecord();
        r1.setRClass(StandardRecordClass.IN);
        r1.setNSName(DomainName.parse("B.ISI.EDU."));
        AResourceRecord r2 = new AResourceRecord();
        MXResourceRecord r3 = new MXResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("USER.EDU."), StandardRecordClass.IN))
            .thenReturn(new Node(DomainName.parse("EDU."), List.of(r1)));
        when(storage.findNodeExact(DomainName.parse("B.ISI.EDU."), StandardRecordClass.IN))
            .thenReturn(new Node(DomainName.parse("B.ISI.EDU."), List.of(r2, r3)));

        NameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("USER.EDU."), StandardRecordType.HINFO, StandardRecordClass.IN)));
        assertEquals(0, response.getAnswer().size());
        assertEquals(1, response.getAuthority().size());
        assertSame(r1, response.getAuthority().get(0));
        assertEquals(1, response.getAdditional().size());
        assertSame(r2, response.getAdditional().get(0));
    }

    @Test
    public void testReferralMultipleNSWithGlueData() {
        NSResourceRecord r1 = new NSResourceRecord();
        r1.setRClass(StandardRecordClass.IN);
        r1.setNSName(DomainName.parse("X.SERVICE.COM."));
        NSResourceRecord r2 = new NSResourceRecord();
        r2.setRClass(StandardRecordClass.IN);
        r2.setNSName(DomainName.parse("Y.SERVICE.COM."));
        NSResourceRecord r3 = new NSResourceRecord();
        r3.setRClass(StandardRecordClass.IN);
        r3.setNSName(DomainName.parse("Z.SERVICE.COM."));
        AResourceRecord r4 = new AResourceRecord();
        MXResourceRecord r5 = new MXResourceRecord();
        AResourceRecord r6 = new AResourceRecord();
        MXResourceRecord r7 = new MXResourceRecord();
        AResourceRecord r8 = new AResourceRecord();
        AResourceRecord r9 = new AResourceRecord();
        HInfoResourceRecord r10 = new HInfoResourceRecord();
        AResourceRecord r11 = new AResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.parse("ACCOUNT.COM."), StandardRecordClass.IN))
            .thenReturn(new Node(DomainName.parse("COM."), List.of(r1, r2, r3)));
        when(storage.findNodeExact(DomainName.parse("X.SERVICE.COM."), StandardRecordClass.IN))
            .thenReturn(new Node(DomainName.parse("X.SERVICE.COM."), List.of(r4, r5, r6, r7, r8)));
        when(storage.findNodeExact(DomainName.parse("Y.SERVICE.COM."), StandardRecordClass.IN))
            .thenReturn(new Node(DomainName.parse("Y.SERVICE.COM."), List.of(r9, r10, r11)));
        when(storage.findNodeExact(DomainName.parse("Z.SERVICE.COM."), StandardRecordClass.IN))
            .thenReturn(null);

        NameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.parse("ACCOUNT.COM."), StandardRecordType.HINFO, StandardRecordClass.IN)));
        assertEquals(0, response.getAnswer().size());
        assertEquals(3, response.getAuthority().size());
        assertSame(r1, response.getAuthority().get(0));
        assertSame(r2, response.getAuthority().get(1));
        assertSame(r3, response.getAuthority().get(2));
        assertEquals(5, response.getAdditional().size());
        assertSame(r4, response.getAdditional().get(0));
        assertSame(r6, response.getAdditional().get(1));
        assertSame(r8, response.getAdditional().get(2));
        assertSame(r9, response.getAdditional().get(3));
        assertSame(r11, response.getAdditional().get(4));
    }
}