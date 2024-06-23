package ahodanenok.dns.core.message;

import java.util.List;

import ahodanenok.dns.core.record.ResourceRecord;

public interface MessageSections {

    List<Question> getQuestion();

    List<ResourceRecord> getAuthority();

    List<ResourceRecord> getAnswer();

    List<ResourceRecord> getAdditional();
}
