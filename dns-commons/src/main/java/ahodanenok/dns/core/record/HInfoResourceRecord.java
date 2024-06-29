package ahodanenok.dns.core.record;

public final class HInfoResourceRecord extends AbstractResourceRecord {

    private String cpu;
    private String os;

    @Override
    public RecordType getType() {
        return StandardRecordType.HINFO;
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
