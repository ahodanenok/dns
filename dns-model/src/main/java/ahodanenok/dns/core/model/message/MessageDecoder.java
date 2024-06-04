package ahodanenok.dns.core.server.message;

import java.io.InputStream;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;

public interface MessageDecoder {

    MessageHeader decodeHeader(InputStream in);

    MessageSections decodeSections(InputStream in);
}
