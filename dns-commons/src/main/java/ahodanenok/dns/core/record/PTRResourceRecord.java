package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public final class PTRResourceRecord extends AbstractResourceRecord {

    private DomainName targetName;

    @Override
    public String getType() {
        return "PTR";
    }

    public DomainName getTargetName() {
        return targetName;
    }

    public void setTargetName(DomainName name) {
        this.targetName = name;
    }
}
