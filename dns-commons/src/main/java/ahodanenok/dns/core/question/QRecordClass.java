package ahodanenok.dns.core.question;

public class QRecordClass {

    private final String name;
    private final int code;

    public QRecordClass(String name, int code) {
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
        if (obj == null || !(obj instanceof QRecordClass)) {
            return false;
        }

        QRecordClass other = (QRecordClass) obj;
        return getCode() == other.getCode();
    }
}
