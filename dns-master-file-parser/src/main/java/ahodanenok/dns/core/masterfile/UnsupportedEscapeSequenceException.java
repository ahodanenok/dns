package ahodanenok.dns.core.masterfile;

import java.io.IOException;

public class UnsupportedEscapeSequenceException extends IOException {

    public UnsupportedEscapeSequenceException(String msg) {
        super(msg);
    }
}
