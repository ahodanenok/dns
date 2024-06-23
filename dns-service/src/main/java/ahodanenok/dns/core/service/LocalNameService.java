package ahodanenok.dns.core.service;

import java.util.ArrayList;
import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.CNameResourceRecord;
import ahodanenok.dns.core.record.NSResourceRecord;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.service.query.Query;
import ahodanenok.dns.core.service.query.QueryResponse;
import ahodanenok.dns.core.service.query.Question;
import ahodanenok.dns.core.storage.NameStorage;
import ahodanenok.dns.core.storage.Node;

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
        List<ResourceRecord> authority = new ArrayList<>();
        List<ResourceRecord> additional = new ArrayList<>();

        while (true) {
            Node node = storage.findNearestNode(qname, question.getQClass());
            if (node == null) {
                // todo: not found
            }

            if (qname.equals(node.getName())) {
                // todo: authoritative
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
                for (ResourceRecord record : node.getRecords()) {
                    if (record.getType().equals("NS")) {
                        authority.add((NSResourceRecord) record);
                    }
                }

                if (!authority.isEmpty()) {
                    // todo: non-authoritative
                    for (ResourceRecord record : authority) {
                        NSResourceRecord nsRecord = (NSResourceRecord) record;
                        Node glueNode = storage.findNodeExact(nsRecord.getNSName(), nsRecord.getRClass());
                        if (glueNode == null) {
                            // todo: what to do?
                            continue;
                        }

                        for (ResourceRecord glueRecord : glueNode.getRecords()) {
                            if (glueRecord.getType().equals("A")) {
                                additional.add(glueRecord);
                            }
                        }
                    }

                    break;
                }

                // todo: not found
                break;
            }
        }

        // todo: what additional records to add?

        return new QueryResponse(answer, authority, additional);
    }
}
