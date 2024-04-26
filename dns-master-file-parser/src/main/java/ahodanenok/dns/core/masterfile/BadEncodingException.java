package ahodanenok.dns.core.masterfile;

import java.io.IOException;

public class BadEncodingException extends IOException {

    public BadEncodingException(String msg) {
        super(msg);
    }
}
