package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ahodanenok.dns.core.message.DefaultMessage;
import ahodanenok.dns.core.message.Operation;
import ahodanenok.dns.core.message.ResponseStatus;
import ahodanenok.dns.core.message.DefaultMessageHeader;
import ahodanenok.dns.core.message.DefaultMessageSections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardMessageEncoderTest {

    @ParameterizedTest
    @ValueSource(ints = { 0x0, 0x1, 0xFF, 0x2345, 0xA9C5, 0xFFFF })
    public void testEncodeHeader_ID(int id) {
        DefaultMessageHeader header = new DefaultMessageHeader(id);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setRecursionAvailable(false);
        header.setRecursionDesired(false);
        header.setTruncated(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) (id >> 8), buf.get());
        assertEquals((byte) id, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_QR_Query() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x1);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setRecursionAvailable(false);
        header.setRecursionDesired(false);
        header.setTruncated(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_QR_Response() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(false);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setRecursionAvailable(false);
        header.setRecursionDesired(false);
        header.setTruncated(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x80, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_OPCODE_Query() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(false);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(true);
        header.setRecursionAvailable(false);
        header.setRecursionDesired(false);
        header.setTruncated(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x84, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_OPCODE_InverseQuery() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.INVERSE_QUERY);
        header.setAuthoritative(false);
        header.setRecursionAvailable(false);
        header.setRecursionDesired(false);
        header.setTruncated(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x8, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_OPCODE_Status() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.STATUS);
        header.setAuthoritative(true);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x15, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_TC_Truncated() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(true);
        header.setRecursionDesired(false);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x2, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_TC_NotTruncated() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(true);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x5, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RD_Desired() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(false);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x81, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RD_NotDesired() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(true);
        header.setRecursionDesired(false);
        header.setRecursionAvailable(true);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x2, buf.get());
        assertEquals((byte) 0x80, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RA_Available() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(false);
        header.setRecursionAvailable(true);
        header.setResponseStatus(ResponseStatus.FORMAT_ERROR);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x81, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RA_NotAvailable() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RCODE_Ok() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.INVERSE_QUERY);
        header.setAuthoritative(true);
        header.setTruncated(true);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(true);
        header.setResponseStatus(ResponseStatus.OK);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0xF, buf.get());
        assertEquals((byte) 0x80, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RCODE_FormatError() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(true);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.FORMAT_ERROR);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x3, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RCODE_ServerFailure() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.SERVER_FAILURE);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x2, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RCODE_NameError() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.NAME_ERROR);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x3, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RCODE_NotImplemented() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.NOT_IMPLEMENTED);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x4, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }

    @Test
    public void testEncodeHeader_RCODE_Refused() {
        DefaultMessageHeader header = new DefaultMessageHeader(0x0);
        header.setQuery(true);
        header.setOperation(Operation.QUERY);
        header.setAuthoritative(false);
        header.setTruncated(false);
        header.setRecursionDesired(true);
        header.setRecursionAvailable(false);
        header.setResponseStatus(ResponseStatus.REFUSED);

        StandardMessageEncoder encoder = new StandardMessageEncoder();
        ByteBuffer buf = encoder.encode(new DefaultMessage(header, new DefaultMessageSections()));
        buf.flip();
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x1, buf.get());
        assertEquals((byte) 0x5, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
        assertEquals((byte) 0x0, buf.get());
    }
}