package ahodanenok.dns.core.masterfile;

import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.zone.ZoneSupplier;

public final class MasterFileZoneSupplier extends ZoneSupplier {

    private final MasterFileParser parser;
    private final String filePath;

    public MasterFileZoneSupplier(MasterFileParser parser, String filePath) {
        this.parser = parser;
        this.filePath = filePath;
    }

    @Override
    protected Iterable<ResourceRecord> getIterable() {
        return parser.parse(filePath);
    }
}
