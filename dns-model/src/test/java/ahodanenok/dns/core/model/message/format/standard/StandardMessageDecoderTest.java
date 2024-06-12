package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ahodanenok.dns.core.model.message.Operation;
import ahodanenok.dns.core.model.message.ResponseStatus;
import ahodanenok.dns.core.model.message.format.MessageDecoderState;
import ahodanenok.dns.core.model.message.format.MessageFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StandardMessageDecoderTest {

    @ParameterizedTest
    @ValueSource(ints = { 0x0, 0x1, 0xFF, 0x2345, 0xA9C5, 0xFFFF })
    public void testDecode_ID_CompleteMessage(int id) {
        ByteBuffer buf = msg16(id, 0x8000, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(id, state.getHeader().getId());
    }

    @Test
    public void testDecode_QR_Query_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertTrue(state.getHeader().isQuery());
    }

    @Test
    public void testDecode_QR_Response_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x8000, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertFalse(state.getHeader().isQuery());
    }

    @Test
    public void testDecode_OPCODE_Query_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x8400, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(Operation.QUERY, state.getHeader().getOperation());
    }

    @Test
    public void testDecode_OPCODE_IQuery_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x8C00, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(Operation.INVERSE_QUERY, state.getHeader().getOperation());
    }

    @Test
    public void testDecode_OPCODE_Status_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x9400, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(Operation.STATUS, state.getHeader().getOperation());
    }

    @Test
    public void testDecode_OPCODE_Unknown_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0xAC00, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageFormatException e = assertThrows(
            MessageFormatException.class, () -> decoder.decode(buf, true));
        assertEquals("Unknown OPCODE: 5", e.getMessage());
    }

    @Test
    public void testDecode_AA_Authoritative_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x400, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertTrue(state.getHeader().isAuthoritative());
    }

    @Test
    public void testDecode_AA_NonAuthoritative_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertFalse(state.getHeader().isAuthoritative());
    }

    @Test
    public void testDecode_TC_Truncated_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x200, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertTrue(state.getHeader().isTruncated());
    }

    @Test
    public void testDecode_AA_NotTruncated_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertFalse(state.getHeader().isTruncated());
    }

    @Test
    public void testDecode_RD_Desired_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x100, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertTrue(state.getHeader().isRecursionDesired());
    }

    @Test
    public void testDecode_RD_NotDesired_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertFalse(state.getHeader().isRecursionDesired());
    }

    @Test
    public void testDecode_RA_Available_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x80, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertTrue(state.getHeader().isRecursionAvailable());
    }

    @Test
    public void testDecode_RA_NotAvailable_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertFalse(state.getHeader().isRecursionAvailable());
    }

    @Test
    public void testDecode_Z_Zeros_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
    }

    @Test
    public void testDecode_Z_NotZeros_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x20, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageFormatException e = assertThrows(
            MessageFormatException.class, () -> decoder.decode(buf, true));
        assertEquals("Field Z must be 0, got 2", e.getMessage());
    }

    @Test
    public void testDecode_RCODE_Ok_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x0, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(ResponseStatus.OK, state.getHeader().getResponseStatus());
    }

    @Test
    public void testDecode_RCODE_FormatError_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x1, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(ResponseStatus.FORMAT_ERROR, state.getHeader().getResponseStatus());
    }

    @Test
    public void testDecode_RCODE_ServerFailure_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x2, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(ResponseStatus.SERVER_FAILURE, state.getHeader().getResponseStatus());
    }

    @Test
    public void testDecode_RCODE_NameError_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x3, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(ResponseStatus.NAME_ERROR, state.getHeader().getResponseStatus());
    }

    @Test
    public void testDecode_RCODE_NotImplemented_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x4, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(ResponseStatus.NOT_IMPLEMENTED, state.getHeader().getResponseStatus());
    }

    @Test
    public void testDecode_RCODE_Refused_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x5, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageDecoderState state = decoder.decode(buf, true);
        assertNotNull(state.getHeader());
        assertEquals(ResponseStatus.REFUSED, state.getHeader().getResponseStatus());
    }

    @Test
    public void testDecode_RCODE_Unknown_CompleteMessage() {
        ByteBuffer buf = msg16(0x1, 0x6, 0x0, 0x0, 0x0, 0x0);

        StandardMessageDecoder decoder = new StandardMessageDecoder();
        MessageFormatException e = assertThrows(
            MessageFormatException.class, () -> decoder.decode(buf, true));
        assertEquals("Unknown RCODE: 6", e.getMessage());
    }

    private ByteBuffer msg16(int... words) {
        ByteBuffer buf = ByteBuffer.allocate(words.length * 2);
        for (int w : words) {
            buf.putShort((short) w);
        }
        buf.rewind();

        return buf;
    }
}