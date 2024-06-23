package ahodanenok.dns.core.message.format;

import java.nio.ByteBuffer;

import ahodanenok.dns.core.message.Message;

public interface MessageEncoder {

    ByteBuffer encode(Message message);
}
