package ahodanenok.dns.core.model.message.format;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;

public interface MessageDecoderState {

    boolean isReady();

    MessageHeader getHeader();

    MessageSections getSections();
}
