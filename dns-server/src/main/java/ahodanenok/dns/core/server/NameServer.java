package ahodanenok.dns.core.server;

import java.util.concurrent.Executors;

import ahodanenok.dns.core.masterfile.DefaultMasterFileParserConfiguration;
import ahodanenok.dns.core.masterfile.MasterFileParser;
import ahodanenok.dns.core.masterfile.MasterFileZoneSupplier;
import ahodanenok.dns.core.model.message.MessageDecoder;
import ahodanenok.dns.core.model.message.MessageEncoder;
import ahodanenok.dns.core.server.request.DefaultRequestHandler;
import ahodanenok.dns.core.server.request.ExecutorServiceRequestProcessor;
import ahodanenok.dns.core.server.request.RequestHandler;
import ahodanenok.dns.core.server.request.RequestProcessor;
import ahodanenok.dns.core.server.transport.UDPTransportHandler;
import ahodanenok.dns.core.service.NameService;
import ahodanenok.dns.core.service.LocalNameService;
import ahodanenok.dns.core.storage.DefaultNameStorage;
import ahodanenok.dns.core.storage.NameStorage;
import ahodanenok.dns.core.zone.ZoneSupplier;

public final class NameServer {

    public static void main(String... args) {
        MasterFileParser masterFileParser = new MasterFileParser(new DefaultMasterFileParserConfiguration());

        ZoneSupplier zoneSupplier = new MasterFileZoneSupplier(masterFileParser, args[0]);

        NameStorage storage = new DefaultNameStorage();
        storage.importZone(zoneSupplier.get());

        NameService service = new LocalNameService(storage);

        RequestHandler requestHandler = new DefaultRequestHandler(service);

        RequestProcessor requestProcessor =
            new ExecutorServiceRequestProcessor(Executors.newSingleThreadExecutor(), requestHandler);

        MessageDecoder messageDecoder = null;
        MessageEncoder messageEncoder = null;

        UDPTransportHandler udp = new UDPTransportHandler(requestProcessor, messageDecoder, messageEncoder);

        udp.activate();
    }
}
