package ahodanenok.dns.core.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class DomainName {

    public static final String ROOT_LABEL = "";
    public static final String LABEL_SEPARATOR = ".";

    public static DomainName of(String... labels) {
        if (labels == null || labels.length == 0) {
            throw new IllegalArgumentException("At least one label is required");
        }

        for (int i = 0; i < labels.length; i++) {
            String label = labels[i];
            if (label == null) {
                throw new IllegalArgumentException("A label can't be null");
            }
            if (label.equals(ROOT_LABEL) && i != labels.length - 1) {
                throw new IllegalArgumentException("The root label can be present only at the end of a name");
            }
        }

        return new DomainName(labels);
    }

    public static DomainName parse(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }

        name = name.trim();
        String[] labels = name.split("\\.");
        if (name.endsWith(LABEL_SEPARATOR)) {
            labels = Arrays.copyOf(labels, labels.length + 1);
            labels[labels.length - 1] = ROOT_LABEL;
        }

        return new DomainName(labels);
    }

    private final String[] labels;

    private DomainName(String[] labels) {
        this.labels = labels;
    }

    public boolean isAbsolute() {
        return labels[labels.length - 1].equals(ROOT_LABEL);
    }

    @Override
    public String toString() {
        String name;
        if (labels.length == 1) {
            name = labels[0];
        } else if (labels.length == 2) {
            name = labels[0] + LABEL_SEPARATOR + labels[1];
        } else if (labels.length == 3) {
            name = labels[0] + LABEL_SEPARATOR + labels[1] + LABEL_SEPARATOR + labels[2];
        } else {
            name = Arrays.stream(labels).collect(Collectors.joining(LABEL_SEPARATOR));
        }

        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        DomainName other = (DomainName) obj;
        if (labels.length != other.labels.length || isAbsolute() != other.isAbsolute()) {
            return false;
        }

        for (int i = 0; i < labels.length; i++) {
            if (!labels[i].equalsIgnoreCase(other.labels[i])) {
                return false;
            }
        }

        return true;
    }
}
