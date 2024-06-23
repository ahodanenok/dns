package ahodanenok.dns.core.message;

import java.io.InputStream;

import ahodanenok.dns.core.message.MessageHeader;
import ahodanenok.dns.core.message.MessageSections;

public interface MessageDecoder {

    MessageHeader decodeHeader(InputStream in);

    MessageSections decodeSections(InputStream in);
}
