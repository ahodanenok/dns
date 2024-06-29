package ahodanenok.dns.core.masterfile;

import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ahodanenok.dns.core.masterfile.record.ResourceRecordParser;
import ahodanenok.dns.core.DomainName;
import ahodanenok.dns.core.record.RecordClass;
import ahodanenok.dns.core.record.RecordType;
import ahodanenok.dns.core.record.ResourceRecord;

public final class MasterFileParser {

    private final FileSystem fs;

    private final int defaultTtl;
    private final RecordClass defaultRClass;
    private final Map<RecordType, ResourceRecordParser<? extends ResourceRecord>> recordParsers;
    private final Set<RecordClass> recordClasses;

    public MasterFileParser(MasterFileParserConfiguration config) {
        this.fs = FileSystems.getDefault();
        this.recordParsers = new HashMap<>(config.recordParsers);
        this.recordClasses = new LinkedHashSet<>(config.recordClasses);
        this.defaultRClass = config.defaultRClass;
        this.defaultTtl = config.defaultTtl;
    }

    public ResourceRecordParseIterator parse(String filePath) {
        InputStream in;
        try {
            in = Files.newInputStream(fs.getPath(filePath));
        } catch (IOException e) {
            throw new MasterFileParseException(String.format("Error while opening file '%s'", filePath), e);
        }

        MasterFileDataReader reader = new MasterFileDataReader(in);
        ParseContext context = new ParseContext();
        context.defaultTtl = defaultTtl;
        context.defaultRClass = defaultRClass;

        return new ResourceRecordParseIterator() {

            @Override
            public boolean hasNext() {
                try {
                    reader.advance();
                    return reader.peek() != MasterFileDataReader.EOF_MARKER;
                } catch (IOException e) {
                    throw new MasterFileParseException("Error while parsing a master file", e);
                }
            }

            @Override
            public ResourceRecord next() {
                try {
                    return parseRecord(reader, context);
                } catch (MasterFileParseException e) {
                    throw e;
                } catch (Exception e) {
                    throw new MasterFileParseException("Error while parsing a master file", e);
                }
            }

            @Override
            public void close() throws Exception {
                reader.close();
            }
        };
    }

    private <R extends ResourceRecord> R parseRecord(MasterFileDataReader reader, ParseContext context) throws Exception {
        reader.advance();

        DomainName name;
        int ttl = -1;
        RecordClass rclass = null;
        RecordType type = null;
        String str;

        str = reader.readString();
        if (str.equals("@")) {
            name = null;
        } else if (str.length() == 1 && CharacterUtils.isBlank(str.charAt(0))) {
            name = null;
        } else {
            name = DomainName.parse(str);
        }

        str = reader.readString();
        if (CharacterUtils.isDigit(str.charAt(0))) {
            ttl = ParseUtils.parseInt(str);
            str = reader.readString();
            rclass = resolveRecordClass(str);
            if (rclass != null) {
                str = reader.readString();
                type = resolveRecordType(str);
                if (type == null) {
                    throw new MasterFileParseException("todo");
                }
            } else {
                type = resolveRecordType(str);
                if (type == null) {
                    throw new MasterFileParseException("todo");
                }
            }
        } else if ((rclass = resolveRecordClass(str)) != null) {
            str = reader.readString();
            if (CharacterUtils.isDigit(str.charAt(0))) {
                ttl = ParseUtils.parseInt(str);
                str = reader.readString();
                type = resolveRecordType(str);
                if (type == null) {
                    throw new MasterFileParseException("todo");
                }
            } else {
                type = resolveRecordType(str);
                if (type == null) {
                    throw new MasterFileParseException("todo");
                }
            }
        } else {
            type = resolveRecordType(str);
            if (type == null) {
                throw new MasterFileParseException("todo");
            }
        }

        if (ttl == -1) {
            ttl = context.defaultTtl;
        } else {
            context.defaultTtl = ttl;
        }

        if (rclass == null) {
            rclass = context.defaultRClass;
        } else {
            context.defaultRClass = rclass;
        }

        if (ttl == -1) {
            throw new MasterFileParseException("todo");
        }
        if (rclass == null) {
            throw new MasterFileParseException("todo");
        }
        if (type == null) {
            throw new MasterFileParseException("todo");
        }

        ResourceRecordParser<R> recordParser = (ResourceRecordParser<R>) recordParsers.get(type);
        R record = recordParser.createInitialRecord();
        record.setName(name);
        record.setRClass(rclass);
        record.setTtl(ttl);
        recordParser.parseRData(reader, record);

        return record;
    }

    private RecordType resolveRecordType(String str) {
        for (RecordType recordType : recordParsers.keySet()) {
            if (recordType.getName().equals(str)) {
                return recordType;
            }
        }

        return null;
    }

    private RecordClass resolveRecordClass(String str) {
        for (RecordClass recordClass : recordClasses) {
            if (recordClass.getName().equals(str)) {
                return recordClass;
            }
        }

        return null;
    }

    private static class ParseContext {

        int defaultTtl;
        RecordClass defaultRClass;
    }
}
