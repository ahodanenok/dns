package ahodanenok.dns.core.service.query;

import java.util.List;

import ahodanenok.dns.core.record.ResourceRecord;

public final class QueryResponse {

    private final List<ResourceRecord> answer;
    private final List<ResourceRecord> authority;
    private final List<ResourceRecord> additional;

    public QueryResponse(List<ResourceRecord> answer, List<ResourceRecord> authority, List<ResourceRecord> additional) {
        this.answer = answer;
        this.authority = authority;
        this.additional = additional;
    }

    public List<ResourceRecord> getAnswer() {
        return answer;
    }

    public List<ResourceRecord> getAuthority() {
        return authority;
    }

    public List<ResourceRecord> getAdditional() {
        return additional;
    }
}
