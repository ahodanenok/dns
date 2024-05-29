package ahodanenok.dns.core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DomainNameTest {

    @Test
    public void testCreateAbsoluteFromLabels_1() {
        DomainName name = DomainName.of("HOLIDAYS", "");
        assertEquals("HOLIDAYS.", name.toString());
        assertTrue(name.isAbsolute());
    }

    @Test
    public void testCreateAbsoluteFromLabels_2() {
        DomainName name = DomainName.of("car", "transport", "");
        assertEquals("car.transport.", name.toString());
        assertTrue(name.isAbsolute());
    }

    @Test
    public void testCreateAbsoluteFromLabels_3() {
        DomainName name = DomainName.of("ROCK", "paper", "SCISSORS", "");
        assertEquals("ROCK.paper.SCISSORS.", name.toString());
        assertTrue(name.isAbsolute());
    }

    @Test
    public void testCreateAbsoluteFromLabels_4() {
        DomainName name = DomainName.of("ds-1", "data", "service", "test", "");
        assertEquals("ds-1.data.service.test.", name.toString());
        assertTrue(name.isAbsolute());
    }

    @Test
    public void testCreateAbsoluteFromLabels_5() {
        DomainName name = DomainName.of("front", "page", "app", "test", "com", "");
        assertEquals("front.page.app.test.com.", name.toString());
        assertTrue(name.isAbsolute());
    }

    @Test
    public void testCreateRelativeFromLabels_1() {
        DomainName name = DomainName.of("images", "content");
        assertEquals("images.content", name.toString());
        assertFalse(name.isAbsolute());
    }

    @Test
    public void testCreateRelativeFromLabels_2() {
        DomainName name = DomainName.of("BRIGHT", "SUN");
        assertEquals("BRIGHT.SUN", name.toString());
        assertFalse(name.isAbsolute());
    }

    @Test
    public void testCreateRelativeFromLabels_3() {
        DomainName name = DomainName.of("db", "app", "com");
        assertEquals("db.app.com", name.toString());
        assertFalse(name.isAbsolute());
    }

    @Test
    public void testCreateRelativeFromLabels_4() {
        DomainName name = DomainName.of("one", "two", "three", "four");
        assertEquals("one.two.three.four", name.toString());
        assertFalse(name.isAbsolute());
    }

    @Test
    public void testCreateRelativeFromLabels_5() {
        DomainName name = DomainName.of("mercury", "venus", "earth", "mars", "jupiter");
        assertEquals("mercury.venus.earth.mars.jupiter", name.toString());
        assertFalse(name.isAbsolute());
    }

    @Test
    public void testCreateFromString() {
        assertEquals("ABC", DomainName.parse("ABC").toString());
        assertEquals("foo.bar", DomainName.parse("foo.bar").toString());
        assertEquals("simple.backend.server.", DomainName.parse("simple.backend.server.").toString());
    }

    @Test
    public void testEqual() {
        assertEquals(DomainName.of(""), DomainName.of(""));
        assertEquals(DomainName.of("abc"), DomainName.of("abc"));
        assertEquals(DomainName.of("aBc"), DomainName.of("AbC"));
        assertEquals(DomainName.of("hello-world"), DomainName.of("hello-world"));
        assertEquals(DomainName.of("hElLO-worLd"), DomainName.of("HeLLo-World"));
        assertEquals(DomainName.of("domain", "name", "SYSTEM"), DomainName.of("DOMAIN", "name", "system"));
        assertEquals(DomainName.of("a.B.C.D.e.f."), DomainName.of("A.b.C.d.e.F."));
    }

    @Test
    public void testNotEqual() {
        assertNotEquals(DomainName.of(""), DomainName.of("a"));
        assertNotEquals(DomainName.of("one"), DomainName.of("two"));
        assertNotEquals(DomainName.of("abc"), DomainName.of("abc."));
        assertNotEquals(DomainName.of("aB"), DomainName.of("AbC"));
        assertNotEquals(DomainName.of("hello-world"), DomainName.of("hello_world"));
        assertNotEquals(DomainName.of("domain", "name", "SYSTEM"), DomainName.of("name", "system"));
        assertNotEquals(DomainName.of("domain", "name", "SYSTEM"), DomainName.of("domain", "name"));
        assertNotEquals(DomainName.of("a.B.C.m.e.f."), DomainName.of("A.b.C.d.e.F."));
        assertNotEquals(DomainName.of("world"), "world");
    }

    @Test
    public void testThrowsWhenLabelIsNull() {
        assertThrows(IllegalArgumentException.class, () -> DomainName.of(null));
        assertThrows(IllegalArgumentException.class, () -> DomainName.of(null, "c"));
        assertThrows(IllegalArgumentException.class, () -> DomainName.of("a", null, "c"));
        assertThrows(IllegalArgumentException.class, () -> DomainName.of("a", "b", "c", null));
    }

    @Test
    public void testThrowsWhenNoLabels() {
        assertThrows(IllegalArgumentException.class, () -> DomainName.of());
        assertThrows(IllegalArgumentException.class, () -> DomainName.of(new String[0]));
    }

    @Test
    public void testThrowsRootLabelIsNotAtTheEnd() {
        assertThrows(IllegalArgumentException.class, () -> DomainName.of("", ""));
        assertThrows(IllegalArgumentException.class, () -> DomainName.of("", "data"));
        assertThrows(IllegalArgumentException.class, () -> DomainName.of("a", "", "b"));
        assertThrows(IllegalArgumentException.class, () -> DomainName.of("a", "b", "c", "", ""));
    }

    @Test
    public void testGetAncestorsAbsolute_1() {
        Iterator<DomainName> ancestors = DomainName.of("").ancestors().iterator();
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testGetAncestorsAbsolute_2() {
        Iterator<DomainName> ancestors = DomainName.of("com", "").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of(""), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testGetAncestorsAbsolute_3() {
        Iterator<DomainName> ancestors = DomainName.of("hello", "world", "").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("world", ""), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of(""), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testGetAncestorsAbsolute_4() {
        Iterator<DomainName> ancestors = DomainName.of("abc", "test", "org", "").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("test", "org", ""), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("org", ""), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of(""), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testGetAncestorsAbsolute_5() {
        Iterator<DomainName> ancestors = DomainName.of("a", "b", "c", "D", "").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("b", "c", "D", ""), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("c", "D", ""), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("D", ""), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of(""), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testAncestorsRelative_1() {
        Iterator<DomainName> ancestors = DomainName.of("service").ancestors().iterator();
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testAncestorsRelative_2() {
        Iterator<DomainName> ancestors = DomainName.of("control", "plane").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("plane"), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testAncestorsRelative_3() {
        Iterator<DomainName> ancestors = DomainName.of("AAA", "BBB", "CCC").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("BBB", "CCC"), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("CCC"), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testAncestorsRelative_4() {
        Iterator<DomainName> ancestors = DomainName.of("a", "b", "c", "d").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("b", "c", "d"), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("c", "d"), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("d"), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testAncestorsRelative_5() {
        Iterator<DomainName> ancestors = DomainName.of("NS", "SOA", "CNAME", "A", "AAAA").ancestors().iterator();
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("SOA", "CNAME", "A", "AAAA"), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("CNAME", "A", "AAAA"), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("A", "AAAA"), ancestors.next());
        assertTrue(ancestors.hasNext());
        assertEquals(DomainName.of("AAAA"), ancestors.next());
        assertFalse(ancestors.hasNext());
        assertThrows(NoSuchElementException.class, () -> ancestors.next());
    }

    @Test
    public void testAncestorsForEach() {
        List<DomainName> ancestors = new ArrayList<>();
        for (DomainName ancestor : DomainName.of("one", "two", "three", "four", "five", "").ancestors()) {
            ancestors.add(ancestor);
        }

        assertEquals(5, ancestors.size());
        assertEquals(DomainName.of("two", "three", "four", "five", ""), ancestors.get(0));
        assertEquals(DomainName.of("three", "four", "five", ""), ancestors.get(1));
        assertEquals(DomainName.of("four", "five", ""), ancestors.get(2));
        assertEquals(DomainName.of("five", ""), ancestors.get(3));
        assertEquals(DomainName.of(""), ancestors.get(4));
    }
}
