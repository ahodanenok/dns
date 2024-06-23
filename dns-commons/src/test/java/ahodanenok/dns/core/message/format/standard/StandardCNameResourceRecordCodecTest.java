package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.RecordType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StandardCNameResourceRecordCodecTest {

    @Test
    public void testRecordType() {
        StandardCNameResourceRecordCodec codec = new StandardCNameResourceRecordCodec();
        assertNotNull(codec.getRecordType());
        assertEquals("CNAME", codec.getRecordType().getName());
    }

    @Test
    public void testDecodeRoot() {
        StandardCNameResourceRecordCodec codec = new StandardCNameResourceRecordCodec();
        CNameResourceRecord record = assertInstanceOf(CNameResourceRecord.class, codec.createInitialRecord());
        assertNull(record.getCanonicalName());

        ByteBuffer buf = ByteBuffer.wrap(new byte[] { 0x0 });
        StandardMessageDecoderState state = new StandardMessageDecoderState();
        state.domainNameDecoder = new StandardDomainNameDecoder();
        codec.decodeRecordData(buf, 1, state, record);
        assertEquals(DomainName.of(""), record.getCanonicalName());
    }

    @Test
    public void testDecodeSingle() {
        StandardCNameResourceRecordCodec codec = new StandardCNameResourceRecordCodec();
        CNameResourceRecord record = assertInstanceOf(CNameResourceRecord.class, codec.createInitialRecord());
        assertNull(record.getCanonicalName());

        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x8, 0x73, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x73, 0x0
        });
        StandardMessageDecoderState state = new StandardMessageDecoderState();
        state.domainNameDecoder = new StandardDomainNameDecoder();
        codec.decodeRecordData(buf, 10, state, record);
        assertEquals(DomainName.of("services", ""), record.getCanonicalName());
    }

    @Test
    public void testDecodeMultiple() {
        StandardCNameResourceRecordCodec codec = new StandardCNameResourceRecordCodec();
        StandardMessageDecoderState state = new StandardMessageDecoderState();
        state.domainNameDecoder = new StandardDomainNameDecoder();

        CNameResourceRecord record_1 = assertInstanceOf(CNameResourceRecord.class, codec.createInitialRecord());
        assertNull(record_1.getCanonicalName());
        ByteBuffer buf_1 = ByteBuffer.wrap(new byte[] { 0x3, 0x61, 0x62, 0x63, 0x0 });
        codec.decodeRecordData(buf_1, 5, state, record_1);
        assertEquals(DomainName.of("abc", ""), record_1.getCanonicalName());

        CNameResourceRecord record_2 = assertInstanceOf(CNameResourceRecord.class, codec.createInitialRecord());
        assertNull(record_2.getCanonicalName());
        assertNotSame(record_1, record_2);
        ByteBuffer buf_2 = ByteBuffer.wrap(new byte[] { 0x1, 0x31, 0x1, 0x32, 0x1, 0x33, 0x0 });
        codec.decodeRecordData(buf_2, 7, state, record_2);
        assertEquals(DomainName.of("1", "2", "3", ""), record_2.getCanonicalName());

        CNameResourceRecord record_3 = assertInstanceOf(CNameResourceRecord.class, codec.createInitialRecord());
        assertNull(record_3.getCanonicalName());
        assertNotSame(record_1, record_3);
        assertNotSame(record_2, record_3);
        ByteBuffer buf_3 = ByteBuffer.wrap(new byte[] {
            0x4, 0x74, 0x65, 0x73, 0x74, 0x4, 0x64, 0x61, 0x74, 0x61, 0x0
        });
        codec.decodeRecordData(buf_3, 11, state, record_3);
        assertEquals(DomainName.of("test", "data", ""), record_3.getCanonicalName());
    }
}
