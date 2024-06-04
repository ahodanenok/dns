package ahodanenok.dns.core.server.transport;

import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.net.PortUnreachableException;

import ahodanenok.dns.core.model.message.MessageDecoder;
import ahodanenok.dns.core.model.message.MessageEncoder;
import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;
import ahodanenok.dns.core.server.request.Response;
import ahodanenok.dns.core.server.request.Request;
import ahodanenok.dns.core.server.request.RequestProcessor;

public final class UDPTransportHandler implements TransportHandler {

    public static final int DATA_LENGTH_BYTES_MAX = 512;

    private final RequestProcessor requestProcessor;
    private final MessageDecoder messageDecoder;
    private final MessageEncoder messageEncoder;

    public UDPTransportHandler(RequestProcessor requestProcessor, MessageDecoder messageDecoder, MessageEncoder messageEncoder) {
        this.requestProcessor = requestProcessor;
        this.messageDecoder = messageDecoder;
        this.messageEncoder = messageEncoder;
    }

    public void activate() {
        try {
            DatagramSocket socket = new DatagramSocket(10053);
            socket.setSoTimeout(0);
        
            while (true) {
                try {
                    DatagramPacket packet = new DatagramPacket(new byte[DATA_LENGTH_BYTES_MAX], DATA_LENGTH_BYTES_MAX);
                    socket.receive(packet);

                    UDPRequest request = new UDPRequest(packet);
                    requestProcessor.processRequest(request);
                } catch (SocketTimeoutException e) {
                    // todo: log?
                    e.printStackTrace();
                } catch (PortUnreachableException e) {
                    // todo: is it possible? log?
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            // todo: log?
            e.printStackTrace();
        }
    }

    private class UDPRequest implements Request {

        private final DatagramPacket packet;
        private final ByteArrayInputStream data;
        private MessageHeader header;
        private MessageSections sections;
        
        UDPRequest(DatagramPacket packet) {
            this.packet = packet;
            this.data = new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength());
        }

        @Override
        public MessageHeader getHeader() {
            if (header == null) {
                header = messageDecoder.decodeHeader(data);
            }

            return header;
        }

        @Override
        public MessageSections getSections() {
            if (sections == null) {
                getHeader();
                sections = messageDecoder.decodeSections(data);
            }

            return sections;
        }

        @Override
        public void complete(Response response) {
            
        }
    }
}
