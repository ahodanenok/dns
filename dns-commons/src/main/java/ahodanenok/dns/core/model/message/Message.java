package ahodanenok.dns.core.model.message;

public interface Message {

    MessageHeader getHeader();

    MessageSections getSections();
}
