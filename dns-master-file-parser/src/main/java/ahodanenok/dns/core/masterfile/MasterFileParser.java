package ahodanenok.dns.core.masterfile;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Map;

import ahodanenok.dns.core.masterfile.record.ResourceRecordParser;
import ahodanenok.dns.core.model.ResourceRecord;

public final class MasterFileParser {

    private final FileSystem fs;
    private final Map<String, ResourceRecordParser<? extends ResourceRecord>> recordParsers;

    public MasterFileParser(MasterFileParserConfiguration config) {
        this.fs = FileSystems.getDefault();
        this.recordParsers = new HashMap<>(config.recordParsers);
    }

    public ResourceRecordParseIterator parse(String filePath) {

        MasterFileDataReader reader = new MasterFileDataReader(null);
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
                    return parseRecord(reader);
                } catch (IOException e) {
                    throw new MasterFileParseException("Error while parsing a master file", e);
                }
            }

            @Override
            public void close() throws Exception {
                // reader.close();
            }
        };
    }

    private ResourceRecord parseRecord(MasterFileDataReader reader) throws IOException {
        reader.advance();

        return null;
    }
}
