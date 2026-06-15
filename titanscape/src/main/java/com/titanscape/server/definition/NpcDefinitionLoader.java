package com.titanscape.server.definition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titanscape.server.model.entity.npc.NpcDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads NPC definitions from JSON files.
 */
public final class NpcDefinitionLoader {

    private static final Logger logger = LogManager.getLogger(NpcDefinitionLoader.class);
    private static final Map<Integer, NpcDefinition> definitions = new HashMap<>();
    private static final Gson GSON = new Gson();

    private NpcDefinitionLoader() {}

    public static void load(String directory) {
        try {
            Path dir = Path.of(directory);
            if (!Files.exists(dir)) {
                logger.warn("NPC definitions directory not found: {}", directory);
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
                for (Path file : stream) {
                    String json = Files.readString(file);
                    Type listType = new TypeToken<List<NpcDefinition>>() {}.getType();
                    List<NpcDefinition> npcs = GSON.fromJson(json, listType);
                    if (npcs != null) {
                        for (NpcDefinition npc : npcs) {
                            definitions.put(npc.getId(), npc);
                        }
                    }
                }
            }
            logger.info("Loaded {} NPC definitions.", definitions.size());
        } catch (IOException e) {
            logger.error("Failed to load NPC definitions", e);
        }
    }

    public static NpcDefinition get(int id) {
        return definitions.get(id);
    }

    public static Map<Integer, NpcDefinition> getAll() {
        return definitions;
    }
}
