package ahodanenok.dns.core.model.message.format.standard;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;
import ahodanenok.dns.core.model.message.format.MessageDecoderState;

public final class StandardMessageDecoderState implements MessageDecoderState {

    private MessageHeader header;

    @Override
    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    @Override
    public MessageSections getSections() {
        return null;
    }
}
