package com.titanscape.server.definition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titanscape.server.model.entity.npc.boss.BossDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads boss definitions from JSON files.
 */
public final class BossDefinitionLoader {

    private static final Logger logger = LogManager.getLogger(BossDefinitionLoader.class);
    private static final Map<Integer, BossDefinition> definitions = new HashMap<>();
    private static final Gson GSON = new Gson();

    private BossDefinitionLoader() {}

    public static void load(String directory) {
        try {
            Path dir = Path.of(directory);
            if (!Files.exists(dir)) {
                logger.warn("Boss definitions directory not found: {}", directory);
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
                for (Path file : stream) {
                    String json = Files.readString(file);
                    Type listType = new TypeToken<List<BossDefinition>>() {}.getType();
                    List<BossDefinition> bosses = GSON.fromJson(json, listType);
                    if (bosses != null) {
                        for (BossDefinition boss : bosses) {
                            definitions.put(boss.getId(), boss);
                        }
                    }
                }
            }
            logger.info("Loaded {} boss definitions.", definitions.size());
        } catch (IOException e) {
            logger.error("Failed to load boss definitions", e);
        }
    }

    public static BossDefinition get(int id) {
        return definitions.get(id);
    }

    public static Map<Integer, BossDefinition> getAll() {
        return definitions;
    }
}
