package com.titanscape.server.definition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titanscape.server.model.item.ItemDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads item definitions from JSON files.
 */
public final class ItemDefinitionLoader {

    private static final Logger logger = LogManager.getLogger(ItemDefinitionLoader.class);
    private static final Map<Integer, ItemDefinition> definitions = new HashMap<>();
    private static final Gson GSON = new Gson();

    private ItemDefinitionLoader() {}

    public static void load(String directory) {
        try {
            Path dir = Path.of(directory);
            if (!Files.exists(dir)) {
                logger.warn("Item definitions directory not found: {}", directory);
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
                for (Path file : stream) {
                    String json = Files.readString(file);
                    Type listType = new TypeToken<List<ItemDefinition>>() {}.getType();
                    List<ItemDefinition> items = GSON.fromJson(json, listType);
                    if (items != null) {
                        for (ItemDefinition item : items) {
                            definitions.put(item.getId(), item);
                        }
                    }
                }
            }
            logger.info("Loaded {} item definitions.", definitions.size());
        } catch (IOException e) {
            logger.error("Failed to load item definitions", e);
        }
    }

    public static ItemDefinition get(int id) {
        return definitions.get(id);
    }

    public static Map<Integer, ItemDefinition> getAll() {
        return definitions;
    }
}
