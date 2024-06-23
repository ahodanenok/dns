package ahodanenok.dns.core.model.message;

public final class DefaultMessageHeader implements MessageHeader {

    private final int id;
    private Operation operation;
    private ResponseStatus responseStatus;
    private boolean query;
    private boolean truncated;
    private boolean authoritative;
    private boolean recursionDesired;
    private boolean recursionAvailable;
    private int questionCount;
    private int answerCount;
    private int authorityCount;
    private int additionalCount;

    public DefaultMessageHeader(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    @Override
    public boolean isAuthoritative() {
        return authoritative;
    }

    public void setAuthoritative(boolean authoritative) {
        this.authoritative = authoritative;
    }

    @Override
    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    @Override
    public boolean isRecursionDesired() {
        return recursionDesired;
    }

    public void setRecursionDesired(boolean recursionDesired) {
        this.recursionDesired = recursionDesired;
    }

    @Override
    public boolean isRecursionAvailable() {
        return recursionAvailable;
    }

    public void setRecursionAvailable(boolean recursionAvailable) {
        this.recursionAvailable = recursionAvailable;
    }

    @Override
    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    @Override
    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    @Override
    public int getAuthorityCount() {
        return authorityCount;
    }

    public void setAuthorityCount(int authorityCount) {
        this.authorityCount = authorityCount;
    }

    @Override
    public int getAdditionalCount() {
        return additionalCount;
    }

    public void setAdditionalCount(int additionalCount) {
        this.additionalCount = additionalCount;
    }
}
