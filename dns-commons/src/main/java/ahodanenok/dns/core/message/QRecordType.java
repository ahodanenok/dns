package ahodanenok.dns.core.message;

public class QRecordType {

    private final String name;
    private final int code;

    public QRecordType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    @Override
    public int hashCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof QRecordType)) {
            return false;
        }

        QRecordType other = (QRecordType) obj;
        return getCode() == other.getCode();
    }
}
