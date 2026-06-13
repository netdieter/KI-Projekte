package de.kivibecoding.protokoll;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class FileSearcher {

    /**
     * Sucht eine Datei mit dem angegebenen Namen rekursiv in einem Verzeichnisbaum.
     */
    public Optional<Path> findFile(Path startDir, String fileName) throws IOException {
        try (Stream<Path> stream = Files.walk(startDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(fileName))
                    .findFirst();
        }
    }

    /**
     * Liest den Inhalt einer Datei.
     */
    public String readFileContent(Path filePath) throws IOException {
        return Files.readString(filePath, StandardCharsets.UTF_8);
    }
}
