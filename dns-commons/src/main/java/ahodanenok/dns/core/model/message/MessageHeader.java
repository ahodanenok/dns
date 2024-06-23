package ahodanenok.dns.core.model.message;

public interface MessageHeader {

    int getId();

    Operation getOperation();

    ResponseStatus getResponseStatus();

    boolean isQuery();

    boolean isAuthoritative();

    boolean isTruncated();

    boolean isRecursionDesired();

    boolean isRecursionAvailable();

    int getQuestionCount();

    int getAnswerCount();

    int getAuthorityCount();

    int getAdditionalCount();
}
