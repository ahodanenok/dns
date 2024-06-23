package ahodanenok.dns.core.message.format;

import ahodanenok.dns.core.message.MessageHeader;
import ahodanenok.dns.core.message.MessageSections;

public interface MessageDecoderState {

    boolean isReady();

    MessageHeader getHeader();

    MessageSections getSections();
}
