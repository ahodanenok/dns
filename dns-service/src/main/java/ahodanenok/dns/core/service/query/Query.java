package ahodanenok.dns.core.service.query;

import java.util.List;

import ahodanenok.dns.core.question.Question;

public final class Query {

    private Question question;

    public Query(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }
}
