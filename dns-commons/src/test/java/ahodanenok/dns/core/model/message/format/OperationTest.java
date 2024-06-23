package ahodanenok.dns.core.model.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class OperationTest {

    @Test
    public void testQuery() {
        assertEquals(Operation.QUERY, Operation.fromCode(0));
    }

    @Test
    public void testIQuery() {
        assertEquals(Operation.INVERSE_QUERY, Operation.fromCode(1));
    }

    @Test
    public void testStatus() {
        assertEquals(Operation.STATUS, Operation.fromCode(2));
    }

    @Test
    public void testUnknown() {
        assertNull(Operation.fromCode(-1));
        for (int code = 3; code < 16; code++) {
            assertNull(Operation.fromCode(code));
        }
    }
}
