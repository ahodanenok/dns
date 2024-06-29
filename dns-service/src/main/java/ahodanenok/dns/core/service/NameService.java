package ahodanenok.dns.core.service;

import ahodanenok.dns.core.service.query.Query;
import ahodanenok.dns.core.service.query.QueryResponse;

public interface NameService {

    QueryResponse processQuery(Query query);
}
