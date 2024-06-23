package ahodanenok.dns.core.zone;

import java.util.List;

import ahodanenok.dns.core.record.ResourceRecord;

public interface ZoneValidator {

    void validateNext(ResourceRecord record);

    void validateFinal();

    List<String> getErrors();
}