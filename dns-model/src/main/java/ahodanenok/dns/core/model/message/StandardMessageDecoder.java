package ahodanenok.dns.core.model.message;

import java.io.InputStream;

public final class StandardMessageDecoder implements MessageDecoder {

    @Override
    public MessageHeader decodeHeader(InputStream in) {
        return null;
    }

    @Override
    public MessageSections decodeSections(InputStream in) {
        return null;
    }
}
