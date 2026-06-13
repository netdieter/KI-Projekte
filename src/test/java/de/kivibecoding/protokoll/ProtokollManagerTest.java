package de.kivibecoding.protokoll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProtokollManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void testAppendAndReadAll() throws IOException {
        Path tempFile = tempDir.resolve("protokoll.jsonl");
        ProtokollManager manager = new ProtokollManager(tempFile);

        ProtokollObjekt obj1 = new ProtokollObjekt("key1", "typ1", "file1");
        ProtokollObjekt obj2 = new ProtokollObjekt("key2", "typ2", "file2");

        manager.append(obj1);
        manager.append(obj2);

        List<ProtokollObjekt> all = manager.readAll();
        assertEquals(2, all.size());
        assertEquals("key1", all.get(0).getKey());
        assertEquals("key2", all.get(1).getKey());
    }

    @Test
    void testGetByKey() throws IOException {
        Path tempFile = tempDir.resolve("protokoll_search.jsonl");
        ProtokollManager manager = new ProtokollManager(tempFile);

        manager.append(new ProtokollObjekt("k1", "t1", "f1"));
        manager.append(new ProtokollObjekt("k2", "t2", "f2"));

        Optional<ProtokollObjekt> found = manager.getByKey("k2");
        assertTrue(found.isPresent());
        assertEquals("k2", found.get().getKey());
        assertEquals("t2", found.get().getTyp());

        Optional<ProtokollObjekt> notFound = manager.getByKey("k3");
        assertFalse(notFound.isPresent());
    }

    @Test
    void testEmptyFile() throws IOException {
        Path tempFile = tempDir.resolve("empty.jsonl");
        ProtokollManager manager = new ProtokollManager(tempFile);

        List<ProtokollObjekt> all = manager.readAll();
        assertTrue(all.isEmpty());

        Optional<ProtokollObjekt> found = manager.getByKey("any");
        assertFalse(found.isPresent());
    }
}
