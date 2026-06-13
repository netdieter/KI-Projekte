package de.kivibecoding.protokoll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileSearcherTest {

    @TempDir
    Path tempDir;

    @Test
    void testFindFileAndReadContent() throws IOException {
        // Create nested structure
        Path subDir = tempDir.resolve("a/b/c");
        Files.createDirectories(subDir);
        Path targetFile = subDir.resolve("target.txt");
        String content = "Hello World";
        Files.writeString(targetFile, content);

        FileSearcher searcher = new FileSearcher();

        // Test find
        Optional<Path> found = searcher.findFile(tempDir, "target.txt");
        assertTrue(found.isPresent());
        assertEquals(targetFile.toAbsolutePath(), found.get().toAbsolutePath());

        // Test read
        String readContent = searcher.readFileContent(found.get());
        assertEquals(content, readContent);
    }

    @Test
    void testFileNotFound() throws IOException {
        FileSearcher searcher = new FileSearcher();
        Optional<Path> found = searcher.findFile(tempDir, "nonexistent.txt");
        assertFalse(found.isPresent());
    }
}
