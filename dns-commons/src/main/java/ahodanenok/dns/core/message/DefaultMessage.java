package ahodanenok.dns.core.message;

public final class DefaultMessage implements Message {

    private final MessageHeader header;
    private final MessageSections sections;

    public DefaultMessage(MessageHeader header, MessageSections sections) {
        this.header = header;
        this.sections = sections;
    }

    @Override
    public MessageHeader getHeader() {
        return header;
    }

    @Override
    public MessageSections getSections() {
        return sections;
    }
}
