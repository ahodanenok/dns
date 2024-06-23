package ahodanenok.dns.core.record;

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
