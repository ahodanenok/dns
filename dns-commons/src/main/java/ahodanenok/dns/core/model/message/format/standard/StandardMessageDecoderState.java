package ahodanenok.dns.core.model.message.format.standard;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;
import ahodanenok.dns.core.model.message.format.MessageDecoderState;

public final class StandardMessageDecoderState implements MessageDecoderState {

    private boolean ready;
    private MessageHeader header;
    private MessageSections sections;

    StandardDomainNameDecoder domainNameDecoder = new StandardDomainNameDecoder();

    @Override
    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public MessageHeader getHeader() {
        return header;
    }

    public void setHeader(MessageHeader header) {
        this.header = header;
    }

    @Override
    public MessageSections getSections() {
        return sections;
    }

    public void setSections(MessageSections sections) {
        this.sections = sections;
    }
}
