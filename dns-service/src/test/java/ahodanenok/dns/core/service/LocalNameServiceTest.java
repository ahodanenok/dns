package ahodanenok.dns.core.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.model.AResourceRecord;
import ahodanenok.dns.core.model.CNameResourceRecord;
import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.HInfoResourceRecord;
import ahodanenok.dns.core.model.MXResourceRecord;
import ahodanenok.dns.core.model.query.Query;
import ahodanenok.dns.core.model.query.QueryResponse;
import ahodanenok.dns.core.model.query.Question;
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
        when(storage.findNearestNode(DomainName.of("db.cluster."), "IN")).thenReturn(
            new Node(DomainName.of("db.cluster."), false, List.of(r1, r2, r3)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.of("db.cluster."), "A", "IN")));
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
        when(storage.findNearestNode(DomainName.of("user.mailbox."), "CS")).thenReturn(
            new Node(DomainName.of("user.mailbox."), false, List.of(r1, r2, r3, r4, r5, r6, r7)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.of("user.mailbox."), "MX", "CS")));
        assertEquals(3, response.getAnswer().size());
        assertSame(r2, response.getAnswer().get(0));
        assertSame(r5, response.getAnswer().get(1));
        assertSame(r6, response.getAnswer().get(2));
    }

    @Test
    public void testExactMatchCName() {
        CNameResourceRecord r1 = new CNameResourceRecord();
        r1.setCanonicalName(DomainName.of("world."));

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.of("hello."), "IN")).thenReturn(
            new Node(DomainName.of("hello."), false, List.of(r1)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.of("hello."), "CNAME", "IN")));
        assertEquals(1, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
    }

    @Test
    public void testFollowOneCName() {
        CNameResourceRecord r1 = new CNameResourceRecord();
        r1.setCanonicalName(DomainName.of("static.data."));
        AResourceRecord r2 = new AResourceRecord();
        MXResourceRecord r3 = new MXResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.of("images."), "IN")).thenReturn(
            new Node(DomainName.of("images."), false, List.of(r1)));
        when(storage.findNearestNode(DomainName.of("static.data."), "IN")).thenReturn(
            new Node(DomainName.of("static.data."), false, List.of(r2, r3)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.of("images."), "A", "IN")));
        assertEquals(2, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
        assertSame(r2, response.getAnswer().get(1));
    }

    @Test
    public void testFollowMultipleCNames() {
        CNameResourceRecord r1 = new CNameResourceRecord();
        r1.setCanonicalName(DomainName.of("one."));
        CNameResourceRecord r2 = new CNameResourceRecord();
        r2.setCanonicalName(DomainName.of("two."));
        CNameResourceRecord r3 = new CNameResourceRecord();
        r3.setCanonicalName(DomainName.of("three."));
        CNameResourceRecord r4 = new CNameResourceRecord();
        r4.setCanonicalName(DomainName.of("four."));
        CNameResourceRecord r5 = new CNameResourceRecord();
        r5.setCanonicalName(DomainName.of("five."));
        AResourceRecord r6 = new AResourceRecord();
        MXResourceRecord r7 = new MXResourceRecord();

        NameStorage storage = mock(NameStorage.class);
        when(storage.findNearestNode(DomainName.of("initial."), "IN")).thenReturn(
            new Node(DomainName.of("initial."), false, List.of(r1)));
        when(storage.findNearestNode(DomainName.of("one."), "IN")).thenReturn(
            new Node(DomainName.of("one."), false, List.of(r2)));
        when(storage.findNearestNode(DomainName.of("two."), "IN")).thenReturn(
            new Node(DomainName.of("two."), false, List.of(r3)));
        when(storage.findNearestNode(DomainName.of("three."), "IN")).thenReturn(
            new Node(DomainName.of("three."), false, List.of(r4)));
        when(storage.findNearestNode(DomainName.of("four."), "IN")).thenReturn(
            new Node(DomainName.of("four."), false, List.of(r5)));
        when(storage.findNearestNode(DomainName.of("five."), "IN")).thenReturn(
            new Node(DomainName.of("five."), false, List.of(r6, r7)));

        LocalNameService service = new LocalNameService(storage);
        QueryResponse response = service.processQuery(
            new Query(new Question(DomainName.of("initial."), "MX", "IN")));
        assertEquals(6, response.getAnswer().size());
        assertSame(r1, response.getAnswer().get(0));
        assertSame(r2, response.getAnswer().get(1));
        assertSame(r3, response.getAnswer().get(2));
        assertSame(r4, response.getAnswer().get(3));
        assertSame(r5, response.getAnswer().get(4));
        assertSame(r7, response.getAnswer().get(5));
    }
}