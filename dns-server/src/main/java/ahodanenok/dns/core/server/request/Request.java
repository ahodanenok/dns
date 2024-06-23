package ahodanenok.dns.core.server.request;

import ahodanenok.dns.core.message.MessageHeader;
import ahodanenok.dns.core.message.MessageSections;

public interface Request {

    MessageHeader getHeader();
    
    MessageSections getSections();

    void complete(Response response);
}
