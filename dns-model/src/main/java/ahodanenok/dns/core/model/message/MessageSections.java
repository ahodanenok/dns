package ahodanenok.dns.core.model.message;

import java.util.List;

import ahodanenok.dns.core.model.ResourceRecord;

public interface MessageSections {

    List<Question> getQuestion();

    List<ResourceRecord> getAuthority();

    List<ResourceRecord> getAnswer();

    List<ResourceRecord> getAdditional();
}
