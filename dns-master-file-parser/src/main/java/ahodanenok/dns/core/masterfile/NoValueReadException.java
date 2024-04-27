package ahodanenok.dns.core.masterfile;

import java.io.IOException;

public class NoValueReadException extends IOException {

    public NoValueReadException(String msg) {
        super(msg);
    }
}
