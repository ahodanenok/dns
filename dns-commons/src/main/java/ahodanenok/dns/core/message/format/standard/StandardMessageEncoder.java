package ahodanenok.dns.core.message.format.standard;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ahodanenok.dns.core.message.Message;
import ahodanenok.dns.core.message.MessageHeader;
import ahodanenok.dns.core.message.format.MessageEncoder;
import ahodanenok.dns.core.question.Question;
import ahodanenok.dns.core.record.RecordClass;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;

public final class StandardMessageEncoder implements MessageEncoder {

    // todo: length limit
    // todo: truncate

    private final Map<RecordType, StandardResourceRecordEncoder<?>> recordEncoders = new HashMap<>();

    public void addRecordEncoder(StandardResourceRecordEncoder<?> recordEncoder) {
        // todo: already registered
        recordEncoders.put(recordEncoder.getRecordType(), recordEncoder);
    }

    public ByteBuffer encode(Message message) {
        try {
            return doEncode(message);
        } catch (Exception e) {
            // todo: custom exception
            throw new RuntimeException(e);
        }
    }

    private ByteBuffer doEncode(Message message) {
        ByteBuffer buf = ByteBuffer.allocate(512);

        MessageHeader header = message.getHeader();
        StandardFormatUtils.encodeUInt16(header.getId(), buf);

        int b = !header.isQuery() ? 0x80 : 0x0;
        b |= header.getOperation().getCode() << 3;
        if (header.isAuthoritative()) {
            b |= 0x4;
        }
        if (header.isTruncated()) {
            b |= 0x2;
        }
        if (header.isRecursionDesired()) {
            b |= 0x1;
        }
        buf.put((byte) b);

        b = header.isRecursionAvailable() ? 0x80 : 0x0;
        // Z=000
        b |= header.getResponseStatus().getCode();
        buf.put((byte) b);

        StandardFormatUtils.encodeUInt16(header.getQuestionCount(), buf);
        StandardFormatUtils.encodeUInt16(header.getAnswerCount(), buf);
        StandardFormatUtils.encodeUInt16(header.getAuthorityCount(), buf);
        StandardFormatUtils.encodeUInt16(header.getAdditionalCount(), buf);

        // maximum for a question and divisable by 32 (just in case)
        ByteBuffer entryBuf = ByteBuffer.allocate(288);

        if (header.getQuestionCount() > 0) {
            Question question;
            List<Question> questions = message.getSections().getQuestion();
            for (int i = 0, n = header.getQuestionCount(); i < n; i++) {
                question = questions.get(i);

                entryBuf.clear();
                StandardFormatUtils.encodeDomainName(question.getQName(), entryBuf);
                StandardFormatUtils.encodeUInt16(question.getQType().getCode(), entryBuf);
                StandardFormatUtils.encodeUInt16(question.getQClass().getCode(), entryBuf);
                // todo: expand buf if needed
                buf.put(entryBuf.flip());
            }
        }

        if (header.getAnswerCount() > 0) {
            List<ResourceRecord> answer = message.getSections().getAnswer();
            for (int i = 0, n = header.getAnswerCount(); i < n; i++) {
                entryBuf.clear();
                encodeRecord(answer.get(i), entryBuf);
                // todo: expand buf if needed
                buf.put(entryBuf.flip());
            }
        }

        if (header.getAuthorityCount() > 0) {
            List<ResourceRecord> authority = message.getSections().getAuthority();
            for (int i = 0, n = header.getAuthorityCount(); i < n; i++) {
                entryBuf.clear();
                encodeRecord(authority.get(i), entryBuf);
                // todo: expand buf if needed
                buf.put(entryBuf.flip());
            }
        }

        if (header.getAdditionalCount() > 0) {
            List<ResourceRecord> additional = message.getSections().getAdditional();
            for (int i = 0, n = header.getAdditionalCount(); i < n; i++) {
                entryBuf.clear();
                encodeRecord(additional.get(i), entryBuf);
                // todo: expand buf if needed
                buf.put(entryBuf.flip());
            }
        }

        return buf;
    }

    private ByteBuffer encodeRecord(ResourceRecord record, ByteBuffer buf) {
        @SuppressWarnings("unchecked")
        StandardResourceRecordEncoder<ResourceRecord> recordEncoder =
            (StandardResourceRecordEncoder<ResourceRecord>) recordEncoders.get(record.getType());
        // todo: record encoder not found

        // len(RDATA) + len(RDLENGTH) + len(TTL) + len(CLASS) + len(TYPE) + len(NAME)
        //   = len(RDATA) + 2 + 4 + 2 + 2 + 255 = len(RDATA) + 265
        int expectedLength = recordEncoder.getExpectedDataLength(record) + 265;
        if (buf.capacity() < expectedLength) {
            buf = ByteBuffer.allocate(Math.min(Math.max(buf.capacity() * 2, expectedLength), 65535));
        }

        StandardFormatUtils.encodeDomainName(record.getName(), buf);
        StandardFormatUtils.encodeUInt16(record.getType().getCode(), buf);
        StandardFormatUtils.encodeUInt16(record.getRClass().getCode(), buf);
        StandardFormatUtils.encodeUInt32(record.getTtl(), buf);
        recordEncoder.encodeRecordData(record, buf);

        return buf;
    }
}
