package ahodanenok.dns.core.record;

import ahodanenok.dns.core.DomainName;

public final class SOAResourceRecord extends AbstractResourceRecord {

    private DomainName masterName;
    private DomainName responsibleName;
    private long serial;
    private int refresh;
    private int retry;
    private int expire;
    private long minTtl;

    @Override
    public String getType() {
        return "SOA";
    }

    public DomainName getMasterName() {
        return masterName;
    }

    public void setMasterName(DomainName name) {
        this.masterName = name;
    }

    public DomainName getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(DomainName name) {
        this.responsibleName = name;
    }

    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public long getMinTtl() {
        return minTtl;
    }

    public void setMinTtl(long ttl) {
        this.minTtl = ttl;
    }
}
