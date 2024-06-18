package ahodanenok.dns.core.model.message.format.standard;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import ahodanenok.dns.core.model.DomainName;
import ahodanenok.dns.core.model.message.format.MessageFormatException;

public final class StandardDomainNameDecoder {

    private static final int BYTE_MASK = 0xFF;
    private static final int LABEL_LENGTH_MAX = 63;
    private static final int NAME_LENGTH_MAX = 255;

    private byte[] label = new byte[LABEL_LENGTH_MAX];
    private String[] labels = new String[8];

    public DomainName decode(ByteBuffer buf) {
        byte b;
        int bytesLeft = NAME_LENGTH_MAX;
        int labelNum = 0;
        int labelLength;
        while (true) {
            labelLength = buf.get() & BYTE_MASK;
            bytesLeft--;

            if (labelLength > LABEL_LENGTH_MAX) {
                throw new MessageFormatException(String.format("Domain name label exceeds %d octets", LABEL_LENGTH_MAX));
            } else if (labelLength == 0) {
                if (labels.length == labelNum) {
                    labels = Arrays.copyOf(labels, labels.length * 2);
                }

                labels[labelNum++] = DomainName.ROOT_LABEL;
                break;
            } else if (bytesLeft == 0) {
                throw new MessageFormatException(String.format("Domain name length exceeds %d octets", NAME_LENGTH_MAX));
            }

            for (int i = 0; i < labelLength; i++) {
                b = buf.get();
                if (b < 0) {
                    throw new MessageFormatException(String.format("Non ascii character: %x", b & BYTE_MASK));
                }
                label[i] = b;
                bytesLeft--;
                if (bytesLeft == 0) {
                    throw new MessageFormatException(String.format("Domain name length exceeds %d octets", NAME_LENGTH_MAX));
                }
            }

            if (labels.length == labelNum) {
                labels = Arrays.copyOf(labels, labels.length * 2);
            }

            labels[labelNum++] = new String(label, 0, labelLength, StandardCharsets.US_ASCII);
        }

        try {
            return DomainName.of(labels, 0, labelNum);
        } catch (Exception e) {
            throw new MessageFormatException("Domain name is not valid", e);
        }
    }
}
