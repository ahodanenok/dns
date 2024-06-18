package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.message.format.MessageFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StandardDomainNameDecoderTest {

    @Test
    public void testDecodeRoot() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of(""), name);
        assertEquals(".", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of(""), name);
        assertEquals(".", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of(""), name);
        assertEquals(".", name.toString());
    }

    @Test
    public void testDecodeFirstLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x65, 0x64, 0x75, // edu
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("edu", ""), name);
        assertEquals("edu.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("edu", ""), name);
        assertEquals("edu.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("edu", ""), name);
        assertEquals("edu.", name.toString());
    }

    @Test
    public void testDecodeSecondLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x7, 0x53, 0x52, 0x49, 0x2D, 0x4E, 0x49, 0x43, // SRI-NIC
            0x4, 0x41, 0x52, 0x50, 0x41, // ARPA
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("sri-nic", "arpa", ""), name);
        assertEquals("SRI-NIC.ARPA.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("sri-nic", "arpa", ""), name);
        assertEquals("SRI-NIC.ARPA.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("sri-nic", "arpa", ""), name);
        assertEquals("SRI-NIC.ARPA.", name.toString());
    }

    @Test
    public void testDecodeThirdLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x7, 0x6A, 0x75, 0x70, 0x69, 0x74, 0x65, 0x72, // jupiter
            0x5, 0x4A, 0x55, 0x4E, 0x49, 0x54, // JUNIT
            0x3, 0x6F, 0x72, 0x67, // org
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("jupiter", "junit", "org", ""), name);
        assertEquals("jupiter.JUNIT.org.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("jupiter", "junit", "org", ""), name);
        assertEquals("jupiter.JUNIT.org.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("jupiter", "junit", "org", ""), name);
        assertEquals("jupiter.JUNIT.org.", name.toString());
    }

    @Test
    public void testDecodeFourthLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x5, 0x65, 0x61, 0x72, 0x74, 0x68, // earth
            0xC, 0x73, 0x6F, 0x6C, 0x61, 0x72, 0x2D, 0x73, 0x79, 0x73, 0x74, 0x65, 0x6D, // solar-system
            0x9, 0x6D, 0x69, 0x6C, 0x6B, 0x79, 0x2D, 0x77, 0x61, 0x79, // milky-way
            0x8, 0x75, 0x6E, 0x69, 0x76, 0x65, 0x72, 0x73, 0x65, // universe
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("earth", "solar-system", "milky-way", "universe", ""), name);
        assertEquals("earth.solar-system.milky-way.universe.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("earth", "solar-system", "milky-way", "universe", ""), name);
        assertEquals("earth.solar-system.milky-way.universe.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("earth", "solar-system", "milky-way", "universe", ""), name);
        assertEquals("earth.solar-system.milky-way.universe.", name.toString());
    }

    @Test
    public void testDecodeFifththLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x6F, 0x6E, 0x65, // one
            0x3, 0x74, 0x77, 0x6F, // two
            0x5, 0x74, 0x68, 0x72, 0x65, 0x65, // three
            0x4, 0x66, 0x6F, 0x75, 0x72, // four
            0x4, 0x66, 0x69, 0x76, 0x65, // five
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("one", "two", "three", "four", "five", ""), name);
        assertEquals("one.two.three.four.five.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("one", "two", "three", "four", "five", ""), name);
        assertEquals("one.two.three.four.five.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("one", "two", "three", "four", "five", ""), name);
        assertEquals("one.two.three.four.five.", name.toString());
    }

    @Test
    public void testDecodeSixthLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x31, 0x30, 0x33, // 103
            0x1, 0x30, // 0
            0x1, 0x33, // 3
            0x2, 0x32, 0x36, // 26
            0x7, 0x49, 0x4E, 0x2D, 0x41, 0x44, 0x44, 0x52, // IN-ADDR
            0x4, 0x41, 0x52, 0x50, 0x41, // ARPA
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("103", "0", "3", "26", "IN-ADDR", "ARPA", ""), name);
        assertEquals("103.0.3.26.IN-ADDR.ARPA.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("103", "0", "3", "26", "IN-ADDR", "ARPA", ""), name);
        assertEquals("103.0.3.26.IN-ADDR.ARPA.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("103", "0", "3", "26", "IN-ADDR", "ARPA", ""), name);
        assertEquals("103.0.3.26.IN-ADDR.ARPA.", name.toString());
    }

    @Test
    public void testDecodeSeventhLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x4, 0x54, 0x68, 0x69, 0x73, // This
            0x5, 0x76, 0x61, 0x6C, 0x75, 0x65, // value
            0x2, 0x69, 0x73, // is
            0x3, 0x73, 0x65, 0x74, // set
            0x2, 0x62, 0x79, // by
            0x3, 0x74, 0x68, 0x65, // the
            0xA, 0x6F, 0x72, 0x69, 0x67, 0x69, 0x6E, 0x61, 0x74, 0x6F, 0x72, // originator
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("this", "value", "is", "set", "by", "the", "originator", ""), name);
        assertEquals("This.value.is.set.by.the.originator.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("this", "value", "is", "set", "by", "the", "originator", ""), name);
        assertEquals("This.value.is.set.by.the.originator.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("this", "value", "is", "set", "by", "the", "originator", ""), name);
        assertEquals("This.value.is.set.by.the.originator.", name.toString());
    }

    @Test
    public void testDecodeEighthLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x54, 0x68, 0x65, // The
            0x6, 0x76, 0x61, 0x6C, 0x75, 0x65, 0x73, // values
            0x3, 0x66, 0x6F, 0x72, // for
            0x4, 0x74, 0x68, 0x69, 0x73, // this
            0x5, 0x66, 0x69, 0x65, 0x6C, 0x64, // field
            0x7, 0x69, 0x6E, 0x63, 0x6C, 0x75, 0x64, 0x65, // include
            0x3, 0x61, 0x6C, 0x6C, // all
            0x5, 0x63, 0x6F, 0x64, 0x65, 0x73, // codes
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("THE", "VALUES", "FOR", "THIS", "FIELD", "INCLUDE", "ALL", "CODES", ""), name);
        assertEquals("The.values.for.this.field.include.all.codes.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("THE", "VALUES", "FOR", "THIS", "FIELD", "INCLUDE", "ALL", "CODES", ""), name);
        assertEquals("The.values.for.this.field.include.all.codes.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("THE", "VALUES", "FOR", "THIS", "FIELD", "INCLUDE", "ALL", "CODES", ""), name);
        assertEquals("The.values.for.this.field.include.all.codes.", name.toString());
    }

    @Test
    public void testDecodeNinthLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x54, 0x68, 0x65, // The
            0x4, 0x6E, 0x61, 0x6D, 0x65, // name
            0x6, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, // server
            0x3, 0x77, 0x61, 0x73, // was
            0x6, 0x75, 0x6E, 0x61, 0x62, 0x6C, 0x65, // unable
            0x2, 0x74, 0x6F, // to
            0x9, 0x69, 0x6E, 0x74, 0x65, 0x72, 0x70, 0x72, 0x65, 0x74, // interpret
            0x3, 0x74, 0x68, 0x65, // the
            0x5, 0x71, 0x75, 0x65, 0x72, 0x79, // query
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("the", "name", "server", "was", "unable", "to", "interpret", "the", "query", ""), name);
        assertEquals("The.name.server.was.unable.to.interpret.the.query.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("the", "name", "server", "was", "unable", "to", "interpret", "the", "query", ""), name);
        assertEquals("The.name.server.was.unable.to.interpret.the.query.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("the", "name", "server", "was", "unable", "to", "interpret", "the", "query", ""), name);
        assertEquals("The.name.server.was.unable.to.interpret.the.query.", name.toString());
    }

    @Test
    public void testDecodeTenthLevel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x4, 0x74, 0x68, 0x69, 0x73, // this
            0x1, 0x34, // 4
            0x3, 0x62, 0x69, 0x74, // bit
            0x5, 0x66, 0x69, 0x65, 0x6C, 0x64, // field
            0x2, 0x69, 0x73, // is
            0x3, 0x73, 0x65, 0x74, // set
            0x2, 0x61, 0x73, // as
            0x4, 0x70, 0x61, 0x72, 0x74, // part
            0x2, 0x6F, 0x66, // of
            0x9, 0x72, 0x65, 0x73, 0x70, 0x6F, 0x6E, 0x73, 0x65, 0x73,// responses
            0x0 // ""
        });

        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("this", "4", "bit", "field", "is", "set", "as", "part", "of", "responses", ""), name);
        assertEquals("this.4.bit.field.is.set.as.part.of.responses.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("this", "4", "bit", "field", "is", "set", "as", "part", "of", "responses", ""), name);
        assertEquals("this.4.bit.field.is.set.as.part.of.responses.", name.toString());

        buf.rewind();
        name = decoder.decode(buf);
        assertEquals(DomainName.of("this", "4", "bit", "field", "is", "set", "as", "part", "of", "responses", ""), name);
        assertEquals("this.4.bit.field.is.set.as.part.of.responses.", name.toString());
    }

    @Test
    public void testDecodeMultipleNames() {
        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();

        ByteBuffer buf_1 = ByteBuffer.wrap(new byte[] {
            0x3, 0x61, 0x62, 0x63, // abc
            0x0 // ""
        });
        DomainName name_1 = decoder.decode(buf_1);
        assertEquals(DomainName.of("abc", ""), name_1);
        assertEquals("abc.", name_1.toString());

        ByteBuffer buf_2 = ByteBuffer.wrap(new byte[] {
            0x5, 0x68, 0x65, 0x6C, 0x6C, 0x6F, // hello
            0x5, 0x77, 0x6F, 0x72, 0x6C, 0x64, // world
            0x0 // ""
        });
        DomainName name_2 = decoder.decode(buf_2);
        assertEquals(DomainName.of("hello", "world", ""), name_2);
        assertEquals("hello.world.", name_2.toString());

        ByteBuffer buf_3 = ByteBuffer.wrap(new byte[] {
            0x8, 0x4D, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x73, // Messages
            0x4, 0x73, 0x65, 0x6E, 0x74, // sent
            0x5, 0x75, 0x73, 0x69, 0x6E, 0x67, // using
            0x3, 0x55, 0x44, 0x50, // UDP
            0x4, 0x75, 0x73, 0x65, 0x72, // user
            0x6, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, // server
            0x4, 0x70, 0x6F, 0x72, 0x74, // port
            0x2, 0x35, 0x33, // 53
            0x0 // ""
        });
        DomainName name_3 = decoder.decode(buf_3);
        assertEquals(DomainName.of("messages", "sent", "using", "UDP", "user", "server", "port", "53", ""), name_3);
        assertEquals("Messages.sent.using.UDP.user.server.port.53.", name_3.toString());

        ByteBuffer buf_4 = ByteBuffer.wrap(new byte[] {
            0x1, 0x41, // A
            0x5, 0x48, 0x49, 0x4E, 0x46, 0x4F, // HINFO
            0x2, 0x4D, 0x58, // MX
            0x0 // ""
        });
        DomainName name_4 = decoder.decode(buf_4);
        assertEquals(DomainName.of("a", "hinfo", "MX", ""), name_4);
        assertEquals("A.HINFO.MX.", name_4.toString());
    }

    @Test
    public void testDecodeLongLabel() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3F,
            // abcdefgabcdefg...abcdefg
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x0 // ""
        });
        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of("abcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefg", ""), name);
        assertEquals("abcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefg.", name.toString());
    }

    @Test
    public void testDecodeLongLabelExceedingMaxLength() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x40,
            // abcdefgabcdefg...abcdefgh
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68,
            0x0 // ""
        });
        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        MessageFormatException e = assertThrows(MessageFormatException.class, () -> decoder.decode(buf));
        assertEquals("Domain name label exceeds 63 octets", e.getMessage());
    }

    @Test
    public void testDecodeLongDomainName() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x54, 0x68, 0x65, // The
            0x5, 0x62, 0x61, 0x73, 0x69, 0x63, // basic
            0x4, 0x69, 0x64, 0x65, 0x61, // idea
            0x2, 0x69, 0x73, // is
            0x4, 0x74, 0x68, 0x61, 0x74, // that
            0x4, 0x77, 0x68, 0x65, 0x6E, // when
            0x3, 0x74, 0x68, 0x65, // the
            0x8, 0x72, 0x65, 0x73, 0x6F, 0x6C, 0x76, 0x65, 0x72, // resolver
            0x3, 0x68, 0x61, 0x73, // has
            0x2, 0x6E, 0x6F, // no
            0x4, 0x69, 0x64, 0x65, 0x61, // idea
            0x4, 0x77, 0x68, 0x61, 0x74, // what
            0x7, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, 0x73, // servers
            0x2, 0x74, 0x6F, // to
            0x3, 0x61, 0x73, 0x6B, // ask
            0x2, 0x69, 0x74, // it
            0x6, 0x73, 0x68, 0x6F, 0x75, 0x6C, 0x64, // should
            0x3, 0x75, 0x73, 0x65, // use
            0xB, 0x69, 0x6E, 0x66, 0x6F, 0x72, 0x6D, 0x61, 0x74, 0x69, 0x6F, 0x6E, // information
            0x4, 0x66, 0x72, 0x6F, 0x6D, // from
            0x1, 0x61, // a
            0xD, 0x63, 0x6F, 0x6E, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6F, 0x6E, // configuration
            0x4, 0x66, 0x69, 0x6C, 0x65, // file
            0x4, 0x74, 0x68, 0x61, 0x74, // that
            0x5, 0x6C, 0x69, 0x73, 0x74, 0x73, // lists
            0x7, 0x73, 0x65, 0x76, 0x65, 0x72, 0x61, 0x6C, // several
            0x7, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, 0x73, // servers
            0x5, 0x77, 0x68, 0x69, 0x63, 0x68, // which
            0x3, 0x61, 0x72, 0x65, // are
            0x8, 0x65, 0x78, 0x70, 0x65, 0x63, 0x74, 0x65, 0x64, // expected
            0x2, 0x74, 0x6F, // to
            0x2, 0x62, 0x65, // be
            0x7, 0x68, 0x65, 0x6C, 0x70, 0x66, 0x75, 0x6C, // helpful
            0x4, 0x72, 0x6F, 0x6F, 0x74, // root
            0x7, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, 0x73, // servers
            0x4, 0x77, 0x69, 0x6C, 0x6C, // will
            0x7, 0x70, 0x72, 0x6F, 0x76, 0x69, 0x64, 0x65, // provide
            0x8, 0x65, 0x76, 0x65, 0x6E, 0x74, 0x75, 0x61, 0x6C, // eventual
            0x6, 0x61, 0x63, 0x63, 0x65, 0x73, 0x73, // access
            0x2, 0x74, 0x6F, // to
            0x3, 0x61, 0x6C, 0x6C, // all
            0x2, 0x6F, 0x66, // of
            0x3, 0x74, 0x68, 0x65, // the
            0x6, 0x64, 0x6F, 0x6D, 0x61, 0x69, 0x6E, // domain
            0x5, 0x73, 0x70, 0x61, 0x63, 0x65, // space
            0x0 // ""
        });
        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        DomainName name = decoder.decode(buf);
        assertEquals(DomainName.of(
            "the", "basic", "idea", "is", "that", "when", "the", "resolver", "has", "no", "idea",
            "what", "servers", "to", "ask", "it", "should", "use", "information", "from", "a",
            "configuration", "file", "that", "lists", "several", "servers", "which", "are", "expected",
            "to", "be", "helpful", "root", "servers", "will", "provide", "eventual", "access", "to",
            "all", "of", "the", "domain", "space", ""), name);
        assertEquals("The.basic.idea.is.that.when.the.resolver.has.no.idea.what.servers.to.ask.it.should.use.information.from.a.configuration.file.that.lists.several.servers.which.are.expected.to.be.helpful.root.servers.will.provide.eventual.access.to.all.of.the.domain.space.", name.toString());
    }

    @Test
    public void testDecodeLongDomainNameExceedingMaxLength() {
        ByteBuffer buf = ByteBuffer.wrap(new byte[] {
            0x3, 0x54, 0x68, 0x65, // The
            0x5, 0x62, 0x61, 0x73, 0x69, 0x63, // basic
            0x4, 0x69, 0x64, 0x65, 0x61, // idea
            0x2, 0x69, 0x73, // is
            0x4, 0x74, 0x68, 0x61, 0x74, // that
            0x4, 0x77, 0x68, 0x65, 0x6E, // when
            0x3, 0x74, 0x68, 0x65, // the
            0x8, 0x72, 0x65, 0x73, 0x6F, 0x6C, 0x76, 0x65, 0x72, // resolver
            0x3, 0x68, 0x61, 0x73, // has
            0x2, 0x6E, 0x6F, // no
            0x4, 0x69, 0x64, 0x65, 0x61, // idea
            0x4, 0x77, 0x68, 0x61, 0x74, // what
            0x7, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, 0x73, // servers
            0x2, 0x74, 0x6F, // to
            0x3, 0x61, 0x73, 0x6B, // ask
            0x2, 0x69, 0x74, // it
            0x6, 0x73, 0x68, 0x6F, 0x75, 0x6C, 0x64, // should
            0x3, 0x75, 0x73, 0x65, // use
            0xB, 0x69, 0x6E, 0x66, 0x6F, 0x72, 0x6D, 0x61, 0x74, 0x69, 0x6F, 0x6E, // information
            0x4, 0x66, 0x72, 0x6F, 0x6D, // from
            0x1, 0x61, // a
            0xD, 0x63, 0x6F, 0x6E, 0x66, 0x69, 0x67, 0x75, 0x72, 0x61, 0x74, 0x69, 0x6F, 0x6E, // configuration
            0x4, 0x66, 0x69, 0x6C, 0x65, // file
            0x4, 0x74, 0x68, 0x61, 0x74, // that
            0x5, 0x6C, 0x69, 0x73, 0x74, 0x73, // lists
            0x7, 0x73, 0x65, 0x76, 0x65, 0x72, 0x61, 0x6C, // several
            0x7, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, 0x73, // servers
            0x5, 0x77, 0x68, 0x69, 0x63, 0x68, // which
            0x3, 0x61, 0x72, 0x65, // are
            0x8, 0x65, 0x78, 0x70, 0x65, 0x63, 0x74, 0x65, 0x64, // expected
            0x2, 0x74, 0x6F, // to
            0x2, 0x62, 0x65, // be
            0x7, 0x68, 0x65, 0x6C, 0x70, 0x66, 0x75, 0x6C, // helpful
            0x4, 0x72, 0x6F, 0x6F, 0x74, // root
            0x7, 0x73, 0x65, 0x72, 0x76, 0x65, 0x72, 0x73, // servers
            0x4, 0x77, 0x69, 0x6C, 0x6C, // will
            0x7, 0x70, 0x72, 0x6F, 0x76, 0x69, 0x64, 0x65, // provide
            0x8, 0x65, 0x76, 0x65, 0x6E, 0x74, 0x75, 0x61, 0x6C, // eventual
            0x6, 0x61, 0x63, 0x63, 0x65, 0x73, 0x73, // access
            0x2, 0x74, 0x6F, // to
            0x3, 0x61, 0x6C, 0x6C, // all
            0x2, 0x6F, 0x66, // of
            0x3, 0x74, 0x68, 0x65, // the
            0x6, 0x64, 0x6F, 0x6D, 0x61, 0x69, 0x6E, // domain
            0x6, 0x73, 0x70, 0x61, 0x63, 0x65, 0x41, // spaceA
            0x0 // ""
        });
        StandardDomainNameDecoder decoder = new StandardDomainNameDecoder();
        MessageFormatException e = assertThrows(MessageFormatException.class, () -> decoder.decode(buf));
        assertEquals("Domain name length exceeds 255 octets", e.getMessage());
    }
}
