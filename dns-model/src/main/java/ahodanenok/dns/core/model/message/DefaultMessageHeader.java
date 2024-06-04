package ahodanenok.dns.core.model.message;

public final class DefaultMessageHeader implements MessageHeader {

    private final int id;

    public DefaultMessageHeader(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}
