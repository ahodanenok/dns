package ahodanenok.dns.core.message;

public interface Message {

    MessageHeader getHeader();

    MessageSections getSections();
}
