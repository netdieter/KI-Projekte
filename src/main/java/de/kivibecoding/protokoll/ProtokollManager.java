package de.kivibecoding.protokoll;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProtokollManager {
    private final Path filePath;
    private final ObjectMapper objectMapper;

    public ProtokollManager(Path filePath) {
        this.filePath = filePath;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Schreibt ein ProtokollObjekt zeilenweise in die Datei.
     */
    public void append(ProtokollObjekt obj) throws IOException {
        String json = objectMapper.writeValueAsString(obj);
        Files.writeString(filePath, json + System.lineSeparator(),
                         StandardCharsets.UTF_8,
                         StandardOpenOption.CREATE,
                         StandardOpenOption.APPEND);
    }

    /**
     * Liest die Datei komplett ein und liefert eine Liste aller ProtokollObjekte.
     */
    public List<ProtokollObjekt> readAll() throws IOException {
        List<ProtokollObjekt> objects = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return objects;
        }
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    objects.add(objectMapper.readValue(line, ProtokollObjekt.class));
                }
            }
        }
        return objects;
    }

    /**
     * Liefert das ProtokollObjekt mit dem entsprechenden Key zurück.
     * Wenn mehrere Objekte denselben Key haben, wird das erste zurückgegeben.
     */
    public Optional<ProtokollObjekt> getByKey(String key) throws IOException {
        if (!Files.exists(filePath)) {
            return Optional.empty();
        }
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    ProtokollObjekt obj = objectMapper.readValue(line, ProtokollObjekt.class);
                    if (key.equals(obj.getKey())) {
                        return Optional.of(obj);
                    }
                }
            }
        }
        return Optional.empty();
    }
}
