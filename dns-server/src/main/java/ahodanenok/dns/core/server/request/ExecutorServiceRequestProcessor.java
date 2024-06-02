package ahodanenok.dns.core.server.request;

import java.util.concurrent.ExecutorService;

public final class ExecutorServiceRequestProcessor implements RequestProcessor {

    private final ExecutorService executorService;
    private final RequestHandler requestHandler;

    public ExecutorServiceRequestProcessor(ExecutorService executorService, RequestHandler requestHandler) {
        this.executorService = executorService;
        this.requestHandler = requestHandler;
    }

    @Override
    public void processRequest(Request request) {

    }
}
