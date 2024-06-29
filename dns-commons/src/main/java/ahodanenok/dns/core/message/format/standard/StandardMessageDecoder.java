package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.RecordClass;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;
import ahodanenok.dns.core.message.DefaultMessageHeader;
import ahodanenok.dns.core.message.DefaultMessageSections;
import ahodanenok.dns.core.message.Operation;
import ahodanenok.dns.core.message.Question;
import ahodanenok.dns.core.message.QRecordClass;
import ahodanenok.dns.core.message.QRecordType;
import ahodanenok.dns.core.message.ResponseStatus;
import ahodanenok.dns.core.message.format.MessageDecoder;
import ahodanenok.dns.core.message.format.MessageFormatException;
import ahodanenok.dns.core.message.format.ResourceRecordDecoder;

public final class StandardMessageDecoder implements MessageDecoder<StandardMessageDecoderState> {

    private static final int BYTE_MASK = 0xFF;

    private final Map<String, StandardResourceRecordDecoder<?>> recordDecoders = new HashMap<>();
    private final List<QRecordType> recordTypes = new ArrayList<>();
    private final List<QRecordClass> recordClasses = new ArrayList<>();

    public void addRecordDecoder(StandardResourceRecordDecoder<?> recordDecoder) {
        // todo: already registered?
        recordDecoders.put(recordDecoder.getRecordType().getName(), recordDecoder);
    }

    public void addQRecordType(QRecordType recordType) {
        // todo: already registered?
        recordTypes.add(recordType);
    }

    public void addQRecordClass(QRecordClass recordClass) {
        // todo: already registered?
        recordClasses.add(recordClass);
    }

    // todo: implement incremental decoding

    @Override
    public StandardMessageDecoderState decode(ByteBuffer buf, boolean last) {
        // header size is 6x16=96bytes

        if (!last) {
            throw new UnsupportedOperationException("Incremental decoding is not implemented");
        }

        StandardMessageDecoderState state = new StandardMessageDecoderState();

        DefaultMessageHeader header = new DefaultMessageHeader(
            ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK));

        byte flags = buf.get();
        header.setQuery((flags & 0x80) == 0);
        Operation operation = Operation.fromCode((flags & 0x78) >> 3);
        if (operation == null) {
            throw new MessageFormatException("Unknown OPCODE: " + ((flags & 0x78) >> 3));
        }
        header.setOperation(operation);
        header.setAuthoritative((flags & 0x4) > 0);
        header.setTruncated((flags & 0x2) > 0);
        header.setRecursionDesired((flags & 0x1) > 0);

        flags = buf.get();
        header.setRecursionAvailable((flags & 0x80) > 0);
        if ((flags & 0x70) != 0) {
            throw new MessageFormatException("Field Z must be 0, got " + ((flags & 0x70) >> 4));
        }
        ResponseStatus responseStatus = ResponseStatus.fromCode(flags & 0xF);
        if (responseStatus == null) {
            throw new MessageFormatException("Unknown RCODE: " + (flags & 0xF));
        }
        header.setResponseStatus(responseStatus);

        header.setQuestionCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));
        header.setAnswerCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));
        header.setAuthorityCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));
        header.setAdditionalCount(((buf.get() << 8) & BYTE_MASK) | (buf.get() & BYTE_MASK));

        state.setHeader(header);

        // max question size 255+16+16=287bytes

        List<Question> question;
        int questionCount = state.getHeader().getQuestionCount();
        if (questionCount > 0) {
            question = new ArrayList<>(questionCount);
            for (int i = 0; i < questionCount; i++) {
                question.add(new Question(
                    state.domainNameDecoder.decode(buf),
                    decodeQRecordType(buf),
                    decodeQRecordClass(buf)));
            }
        } else {
            question = List.of();
        }

        List<ResourceRecord> answer;
        int answerCount = state.getHeader().getAnswerCount();
        if (answerCount > 0) {
            answer = new ArrayList<>(answerCount);
            for (int i = 0; i < answerCount; i++) {
                answer.add(decodeResourceRecord(buf, state));
            }
        } else {
            answer = List.of();
        }

        state.setSections(new DefaultMessageSections(question, List.of(), List.of(), List.of()));
        state.setReady(true);

        return state;
    }

    @Override
    public void decode(ByteBuffer buf, StandardMessageDecoderState state, boolean last) {
        throw new UnsupportedOperationException("Incremental decoding is not implemented");
    }

    private QRecordType decodeQRecordType(ByteBuffer buf) {
        int recordTypeCode = ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK);
        for (QRecordType recordType : recordTypes) {
            if (recordType.getCode() == recordTypeCode) {
                return recordType;
            }
        }

        throw new MessageFormatException("Unknown QTYPE: " + recordTypeCode);
    }

    private QRecordClass decodeQRecordClass(ByteBuffer buf) {
        int recordClassCode = ((buf.get() & BYTE_MASK) << 8) | (buf.get() & BYTE_MASK);
        for (QRecordClass recordClass : recordClasses) {
            if (recordClass.getCode() == recordClassCode) {
                return recordClass;
            }
        }

        throw new MessageFormatException("Unknown QCLASS: " + recordClassCode);
    }

    private <R extends ResourceRecord> R decodeResourceRecord(ByteBuffer buf, StandardMessageDecoderState state) {
        DomainName name = state.domainNameDecoder.decode(buf);
        // assuming records won't have QTYPE, QCLAS values
        RecordType recordType = (RecordType) decodeQRecordType(buf);
        RecordClass recordClass = (RecordClass) decodeQRecordClass(buf);

        @SuppressWarnings("unchecked")
        StandardResourceRecordDecoder<R> recordDecoder =
            (StandardResourceRecordDecoder<R>) recordDecoders.get(recordType.getName());
        if (recordDecoder == null) {
            throw new MessageFormatException("No record decoder for TYPE: " + recordType.getName());
        }

        R record = recordDecoder.createInitialRecord();
        record.setName(name);
        record.setRClass(recordClass);
        record.setTtl(0);

        int dataLength = StandardFormatUtils.decodeUInt16(buf);
        recordDecoder.decodeRecordData(buf, dataLength, state, record);

        return record;
    }
}
