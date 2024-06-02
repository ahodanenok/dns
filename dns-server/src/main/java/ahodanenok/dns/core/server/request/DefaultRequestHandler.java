package ahodanenok.dns.core.server.request;

import ahodanenok.dns.core.service.NameService;

public final class DefaultRequestHandler implements RequestHandler {

    private final NameService nameService;

    public DefaultRequestHandler(NameService nameService) {
        this.nameService = nameService;
    }

    @Override
    public void handleRequest(Request request) {
        nameService.processQuery(request.getQuery());
    }
}
