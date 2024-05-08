package ahodanenok.dns.core.masterfile;

import java.nio.file.Path;

public final class FileUtils {

    public static String getTestFilePath(String fileName) throws Exception {
        return Path.of(FileUtils.class.getResource(fileName).toURI()).toString();
    }
}
