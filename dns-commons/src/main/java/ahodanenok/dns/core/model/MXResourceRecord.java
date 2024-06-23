package ahodanenok.dns.core.model;

public final class MXResourceRecord extends AbstractResourceRecord {

    private int preference;
    private DomainName exchange;

    @Override
    public String getType() {
        return "MX";
    }

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public DomainName getExchange() {
        return exchange;
    }

    public void setExchange(DomainName exchange) {
        this.exchange = exchange;
    }
}
