package ahodanenok.dns.core.message;

import java.util.List;

import ahodanenok.dns.core.record.ResourceRecord;

public final class DefaultMessageSections implements MessageSections {

    private final List<Question> question;
    private final List<ResourceRecord> answer;
    private final List<ResourceRecord> authority;
    private final List<ResourceRecord> additional;

    public DefaultMessageSections() {
        this(List.of(), List.of(), List.of(), List.of());
    }

    public DefaultMessageSections(List<Question> question, List<ResourceRecord> answer, List<ResourceRecord> authority, List<ResourceRecord> additional) {
        this.question = question;
        this.answer = answer;
        this.authority = authority;
        this.additional = additional;
    }

    @Override
    public List<Question> getQuestion() {
        return question;
    }

    @Override
    public List<ResourceRecord> getAnswer() {
        return answer;
    }

    @Override
    public List<ResourceRecord> getAuthority() {
        return authority;
    }

    @Override
    public List<ResourceRecord> getAdditional() {
        return additional;
    }
}
