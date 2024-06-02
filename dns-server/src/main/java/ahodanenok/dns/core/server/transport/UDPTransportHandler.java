package ahodanenok.dns.core.server.transport;

import ahodanenok.dns.core.server.request.RequestProcessor;

public final class UDPTransportHandler implements TransportHandler {

    private final RequestProcessor requestProcessor;

    public UDPTransportHandler(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    public void activate() {

    }
}
