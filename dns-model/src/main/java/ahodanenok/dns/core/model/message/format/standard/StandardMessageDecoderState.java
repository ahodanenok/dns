package ahodanenok.dns.core.model.message.format.standard;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;
import ahodanenok.dns.core.model.message.format.MessageDecoderState;

public final class StandardMessageDecoderState implements MessageDecoderState {

    @Override
    public MessageHeader getHeader() {
        return null;
    }

    @Override
    public MessageSections getSections() {
        return null;
    }
}
