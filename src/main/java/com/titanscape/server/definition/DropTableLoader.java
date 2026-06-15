package com.titanscape.server.definition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public final class DropTableLoader {

    private static final Logger logger = LogManager.getLogger(DropTableLoader.class);
    private static final Map<Integer, List<DropEntry>> dropTables = new HashMap<>();
    private static final Gson GSON = new Gson();

    private DropTableLoader() {}

    public static void load(String directory) {
        try {
            Path dir = Path.of(directory);
            if (!Files.exists(dir)) {
                logger.warn("Drop table directory not found: {}", directory);
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
                for (Path file : stream) {
                    String json = Files.readString(file);
                    Type type = new TypeToken<Map<String, List<DropEntry>>>() {}.getType();
                    Map<String, List<DropEntry>> tables = GSON.fromJson(json, type);
                    if (tables != null) {
                        for (var entry : tables.entrySet()) {
                            dropTables.put(Integer.parseInt(entry.getKey()), entry.getValue());
                        }
                    }
                }
            }
            logger.info("Loaded {} drop tables.", dropTables.size());
        } catch (IOException e) {
            logger.error("Failed to load drop tables", e);
        }
    }

    public static List<DropEntry> getDrops(int npcId) {
        return dropTables.getOrDefault(npcId, Collections.emptyList());
    }

    public static class DropEntry {
        public int itemId;
        public int minAmount;
        public int maxAmount;
        public double chance;
        public String rarity;
    }
}
