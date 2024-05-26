package ahodanenok.dns.core.service;

import java.util.ArrayList;
import java.util.List;

import ahodanenok.dns.core.model.CNameResourceRecord;
import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.ResourceRecord;
import ahodanenok.dns.core.model.query.Query;
import ahodanenok.dns.core.model.query.QueryResponse;
import ahodanenok.dns.core.model.query.Question;
import ahodanenok.dns.core.storage.NameStorage;
import ahodanenok.dns.core.storage.Node;

import ahodanenok.dns.core.model.ResourceRecord;

public final class LocalNameService implements NameService {

    private final NameStorage storage;

    public LocalNameService(NameStorage storage) {
        this.storage = storage;
    }

    @Override
    public QueryResponse processQuery(Query query) {
        Question question = query.getQuestion();
        DomainName qname = question.getQName();
        List<ResourceRecord> answer = new ArrayList<>();

        while (true) {
            Node node = storage.findNearestNode(qname, question.getQClass());
            if (qname.equals(node.getName())) {
                CNameResourceRecord cnameRecord = node.getRecords().stream()
                    .filter(r -> r.getType().equals("CNAME"))
                    .findFirst()
                    .map(CNameResourceRecord.class::cast)
                    .orElse(null);
                if (cnameRecord != null && !question.getQType().equals("CNAME")) {
                    answer.add(cnameRecord);
                    qname = cnameRecord.getCanonicalName();
                } else {
                    for (ResourceRecord record : node.getRecords()) {
                        if (record.getType().equals(question.getQType())) {
                            answer.add(record);
                        }
                    }
                    break;
                }
            } else {
                // todo: ns
                // todo: not found
                break;
            }
        }

        // todo: what additional records to add?

        return new QueryResponse(answer, List.of(), List.of());
    }
}
