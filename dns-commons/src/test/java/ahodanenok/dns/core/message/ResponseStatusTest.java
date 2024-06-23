package ahodanenok.dns.core.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResponseStatusTest {

    @Test
    public void testOk() {
        assertEquals(ResponseStatus.OK, ResponseStatus.fromCode(0));
    }

    @Test
    public void testFormatError() {
        assertEquals(ResponseStatus.FORMAT_ERROR, ResponseStatus.fromCode(1));
    }

    @Test
    public void testServerFailure() {
        assertEquals(ResponseStatus.SERVER_FAILURE, ResponseStatus.fromCode(2));
    }

    @Test
    public void testNameError() {
        assertEquals(ResponseStatus.NAME_ERROR, ResponseStatus.fromCode(3));
    }

    @Test
    public void testNotImplemented() {
        assertEquals(ResponseStatus.NOT_IMPLEMENTED, ResponseStatus.fromCode(4));
    }

    @Test
    public void testRefused() {
        assertEquals(ResponseStatus.REFUSED, ResponseStatus.fromCode(5));
    }

    @Test
    public void testUndefined() {
        assertNull(ResponseStatus.fromCode(-1));
        for (int code = 6; code < 16; code++) {
            assertNull(ResponseStatus.fromCode(code));
        }
    }
}