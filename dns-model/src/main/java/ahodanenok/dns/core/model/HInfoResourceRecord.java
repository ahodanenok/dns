package ahodanenok.dns.core.model;

public final class HInfoResourceRecord extends AbstractResourceRecord {

    private String cpu;
    private String os;

    @Override
    public String getType() {
        return "HINFO";
    }

    public String getCPU() {
        return cpu;
    }

    public void setCPU(String cpu) {
        this.cpu = cpu;
    }

    public String getOS() {
        return os;
    }

    public void setOS(String os) {
        this.os = os;
    }
}
