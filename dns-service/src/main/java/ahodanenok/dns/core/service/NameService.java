package ahodanenok.dns.core.service;

import ahodanenok.dns.core.service.query.Query;
import ahodanenok.dns.core.service.query.QueryResponse;
import ahodanenok.dns.core.service.query.Question;

public interface NameService {

    QueryResponse processQuery(Query query);
}
