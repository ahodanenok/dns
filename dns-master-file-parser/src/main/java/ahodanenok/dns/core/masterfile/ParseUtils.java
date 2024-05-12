package ahodanenok.dns.core.masterfile;

import java.io.IOException;

import ahodanenok.dns.core.model.DomainName;

public final class ParseUtils {

    public static DomainName parseDomainName(MasterFileDataReader reader) {
        try {
            return DomainName.of(reader.readString());
        } catch (IOException e) {
            throw new MasterFileParseException("todo", e);
        }
    }

    public static int parseInt(MasterFileDataReader reader) {
        try {
            return parseInt(reader.readString());
        } catch (IOException e) {
            throw new MasterFileParseException("todo", e);
        }
    }

    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new MasterFileParseException("todo", e);
        }
    }

    public static long parseUnsignedInt(MasterFileDataReader reader) {
        try {
            return Long.parseLong(reader.readString());
        } catch (IOException | NumberFormatException e) {
            throw new MasterFileParseException("todo", e);
        }
    }
}
