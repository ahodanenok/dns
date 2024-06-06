package ahodanenok.dns.core.server.transport;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;

import ahodanenok.dns.core.model.message.MessageHeader;
import ahodanenok.dns.core.model.message.MessageSections;
import ahodanenok.dns.core.model.message.format.MessageDecoder;
import ahodanenok.dns.core.model.message.format.MessageDecoderState;
import ahodanenok.dns.core.model.message.format.MessageEncoder;
import ahodanenok.dns.core.server.request.Response;
import ahodanenok.dns.core.server.request.Request;
import ahodanenok.dns.core.server.request.RequestProcessor;

public final class UDPTransportHandler implements TransportHandler {

    public static final int DATA_LENGTH_BYTES_MAX = 512;

    private final RequestProcessor requestProcessor;
    private final MessageDecoder<?> messageDecoder;
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
        private MessageHeader header;
        private MessageSections sections;

        UDPRequest(DatagramPacket packet) {
            this.packet = packet;
        }

        @Override
        public MessageHeader getHeader() {
            if (header == null) {
                readMessage();
            }

            return header;
        }

        @Override
        public MessageSections getSections() {
            if (sections == null) {
                readMessage();
            }

            return sections;
        }

        private void readMessage() {
            MessageDecoderState state = messageDecoder.decode(
                ByteBuffer.wrap(packet.getData(), packet.getOffset(), packet.getLength()),
                true);
            this.header = state.getHeader();
            this.sections = state.getSections();
        }

        @Override
        public void complete(Response response) {

        }
    }
}
