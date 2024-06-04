package ahodanenok.dns.core.server.request;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;

public interface Request {

    MessageHeader getHeader();
    
    MessageSections getSections();

    void complete(Response response);
}
