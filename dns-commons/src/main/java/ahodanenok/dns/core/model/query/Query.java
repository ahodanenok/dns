package ahodanenok.dns.core.model.query;

import java.util.List;

public final class Query {

    private Question question;

    public Query(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
