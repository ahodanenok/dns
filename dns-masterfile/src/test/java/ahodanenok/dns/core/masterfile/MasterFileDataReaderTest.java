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
    public void testReadStringEOF() throws IOException {
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

    @ParameterizedTest
    @ValueSource(strings = { "mercury", "mercury\t", "mercury ", "mercury\t \t "})
    public void testReadUnquotedString(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(str.getBytes(StandardCharsets.US_ASCII)));
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
    @ValueSource(strings = { "Domain\tName\tSystem", "Domain \t Name\t \tSystem "})
    public void testReadMultipleUnquotedStrings(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(str.getBytes(StandardCharsets.US_ASCII)));
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

    @Test
    public void testReadCommentIgnoreEscapedLineSeparator() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format("special ;encodings\\%nare necessary").getBytes(StandardCharsets.US_ASCII)));
        assertEquals("special", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringWithEscapedComment() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("definitions\\;expected;to occur".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("definitions;expected", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringWithEscapedBlanks() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("domain\\\tname\\ system".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("domain\tname system", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringWithLineSeparator() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format("name\\%nserver").getBytes(StandardCharsets.US_ASCII)));
        assertEquals(String.format("name%nserver"), reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringWithUnsupportedEscapeSequence() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format("ab\\3cd").getBytes(StandardCharsets.US_ASCII)));
        UnsupportedEscapeSequenceException e =
            assertThrows(UnsupportedEscapeSequenceException.class, () -> reader.readString());
        assertEquals("\\3", e.getMessage());
    }

    @Test
    public void testAdvanceNoData() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(new ByteArrayInputStream(new byte[0]));
        assertDoesNotThrow(() -> reader.advance());
        assertDoesNotThrow(() -> reader.advance());
        assertDoesNotThrow(() -> reader.advance());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @ParameterizedTest
    @ValueSource(strings = { "cname", "%ncname", ";address%ncname", "%n \t%n%ncname"})
    public void testAdvanceReadUnquotedString(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format(str).getBytes(StandardCharsets.US_ASCII)));
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("cname", reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "decimal integer", "%ndecimal integer", "decimal%ninteger", "%ndecimal%ninteger",
        ";positive%ndecimal;hex%ninteger", " %n\t%n  %n%ndecimal %n  %n\t\t\t%n%ninteger",
        "decimal integer%n%n"
    })
    public void testAdvanceReadUnquotedStringMutliple(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format(str).getBytes(StandardCharsets.US_ASCII)));
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("decimal", reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("integer", reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @ParameterizedTest
    @ValueSource(strings = { ";domain", " \t", "" })
    public void testPeekEOF(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format(str).getBytes(StandardCharsets.US_ASCII)));
        assertEquals(MasterFileDataReader.EOF_MARKER, reader.peek());
        assertEquals(MasterFileDataReader.EOF_MARKER, reader.peek());
        assertEquals(MasterFileDataReader.EOF_MARKER, reader.peek());
    }

    @Test
    public void testPeekSpace() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(" \trecord".getBytes(StandardCharsets.US_ASCII)));
        assertEquals(' ', reader.peek());
        assertEquals(' ', reader.peek());
        assertEquals(' ', reader.peek());
    }

    @Test
    public void testPeekTab() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("\t record".getBytes(StandardCharsets.US_ASCII)));
        assertEquals('\t', reader.peek());
        assertEquals('\t', reader.peek());
        assertEquals('\t', reader.peek());
    }

    @Test
    public void testPeek() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("record".getBytes(StandardCharsets.US_ASCII)));
        assertEquals('r', reader.peek());
        assertEquals('r', reader.peek());
        assertEquals('r', reader.peek());
    }

    @ParameterizedTest
    @ValueSource(strings = { "%ndomain", " \t%ndomain" })
    public void testPeekEOL(String str) throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format(str).getBytes(StandardCharsets.US_ASCII)));
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
    }

    @Test
    public void testReadInitialBlank() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(" NS VENERA".getBytes(StandardCharsets.US_ASCII)));
        assertEquals(" ", reader.readString());
        assertEquals("NS", reader.readString());
        assertEquals("VENERA", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringWithEscapedSpace() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("\\ mailbox".getBytes(StandardCharsets.US_ASCII)));
        assertEquals(" mailbox", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadUnquotedStringWithEscapedTab() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream("\\\tmailbox".getBytes(StandardCharsets.US_ASCII)));
        assertEquals("\tmailbox", reader.readString());
        assertThrows(EOFException.class, () -> reader.readString());
    }

    @Test
    public void testReadMultipleLinesWithInitialBlank() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format(
                "  IN\tA  192.0.2.1 ; IPv4 address%n"
                + "\t IN  NS dns ; nameserver %n"
                + " MX a.test.com%n"
                + "\tMX 200 b.test.com").getBytes(StandardCharsets.US_ASCII)));
        assertEquals(" ", reader.readString());
        assertEquals("IN", reader.readString());
        assertEquals("A", reader.readString());
        assertEquals("192.0.2.1", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("\t", reader.readString());
        assertEquals("IN", reader.readString());
        assertEquals("NS", reader.readString());
        assertEquals("dns", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals(" ", reader.readString());
        assertEquals("MX", reader.readString());
        assertEquals("a.test.com", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("\t", reader.readString());
        assertEquals("MX", reader.readString());
        assertEquals("200", reader.readString());
        assertEquals("b.test.com", reader.readString());
        assertEquals(MasterFileDataReader.EOF_MARKER, reader.peek());
        assertThrows(EOFException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
    }

    @Test
    public void testReadMultipleLines() throws IOException {
        MasterFileDataReader reader = new MasterFileDataReader(
            new ByteArrayInputStream(String.format(
                "test.com.\t\tIN A 127.0.0.1;localhost%n"
                + "www   IN  CNAME a.com. %n"
                + "      NS  gw-1.earth%n"
                + "@ MX\tno-reply%n"
                + "\tAAAA   0:0:0:0:0:0:0:1 ;localhost").getBytes(StandardCharsets.US_ASCII)));
        assertEquals("test.com.", reader.readString());
        assertEquals("IN", reader.readString());
        assertEquals("A", reader.readString());
        assertEquals("127.0.0.1", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("www", reader.readString());
        assertEquals("IN", reader.readString());
        assertEquals("CNAME", reader.readString());
        assertEquals("a.com.", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals(" ", reader.readString());
        assertEquals("NS", reader.readString());
        assertEquals("gw-1.earth", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("@", reader.readString());
        assertEquals("MX", reader.readString());
        assertEquals("no-reply", reader.readString());
        assertEquals(MasterFileDataReader.EOL_MARKER, reader.peek());
        assertThrows(NoValueReadException.class, () -> reader.readString());
        assertDoesNotThrow(() -> reader.advance());
        assertDoesNotThrow(() -> reader.advance());
        assertEquals("\t", reader.readString());
        assertEquals("AAAA", reader.readString());
        assertEquals("0:0:0:0:0:0:0:1", reader.readString());
        assertEquals(MasterFileDataReader.EOF_MARKER, reader.peek());
        assertThrows(EOFException.class, () -> reader.readString());
    }
}
