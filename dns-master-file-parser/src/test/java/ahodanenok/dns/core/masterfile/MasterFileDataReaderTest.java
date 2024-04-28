package ahodanenok.dns.core.masterfile;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class MasterFileDataReaderTest {

    @Test
    public void testReadStringNoData() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(new byte[0]));
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadStringBadCharacter() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(new byte[] { (byte) 0xF1 }));
        assertThrows(BadEncodingException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedString() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("mercury".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("mercury", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringPreserveCase() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("WaTeRfalL".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("WaTeRfalL", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @ParameterizedTest
    @ValueSource(strings = { " venus", "\tvenus", "venus ", "venus\t",  " \t venus\t \t" })
    public void testReadUnquotedStringSkipBlanks(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(str.getBytes(StandardCharsets.US_ASCII)));
        assertEquals("venus", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadMultipleUnquotedStrings() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("hello\tworld".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("hello", reader.readString());
        assertEquals("world", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadMultipleUnquotedStringsSkipBlanks() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("\t Domain \t Name\t\tSystem ".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("Domain", reader.readString());
        assertEquals("Name", reader.readString());
        assertEquals("System", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringUntilNewLine() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format("mars%n").getBytes(StandardCharsets.US_ASCII)));
        assertEquals("mars", reader.readString());
        assertThrows(NoValueReadException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringDontMoveToNextLine() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format("first%nsecond").getBytes(StandardCharsets.US_ASCII)));
        assertEquals("first", reader.readString());
        assertThrows(NoValueReadException.class, () -> reader.readString());
    }

    @ParameterizedTest
    @ValueSource(strings = { "; nothing here", " ; nothing here", "\t; nothing here" })
    public void testReadEmptyStringWithComment(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(str.getBytes(StandardCharsets.US_ASCII)));
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringIgnoreComment() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("hello;world".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("hello", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }
}
