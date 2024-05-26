package ahodanenok.dns.core.service;

import ahodanenok.dns.core.model.query.Query;
import ahodanenok.dns.core.model.query.QueryResponse;
import ahodanenok.dns.core.model.query.Question;

public interface NameService {

    QueryResponse processQuery(Query query);
}
