package ahodanenok.dns.core.model;

public final class AResourceRecord extends AbstractResourceRecord {

    // todo: use InetAddress?
    private String address;

    @Override
    public String getType() {
        return "A";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
