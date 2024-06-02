package ahodanenok.dns.core.server.request;

import ahodanenok.dns.core.model.query.Query;
import ahodanenok.dns.core.model.query.QueryResponse;

public interface Request {

    Query getQuery();

    void complete(QueryResponse response);
}
