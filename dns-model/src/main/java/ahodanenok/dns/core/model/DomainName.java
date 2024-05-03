package ahodanenok.dns.core.model;

public final class DomainName {

    public static DomainName of(String name) {
        return new DomainName(name);
    }

    private final String value;

    private DomainName(String name) {
        this.value = name;
    }

    @Override
    public String toString() {
        return value;
    }
}
