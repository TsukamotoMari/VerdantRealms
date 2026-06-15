package com.titanscape.server.definition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titanscape.server.model.content.shop.ShopDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ShopDefinitionLoader {

    private static final Logger logger = LogManager.getLogger(ShopDefinitionLoader.class);
    private static final Map<String, ShopDefinition> definitions = new HashMap<>();
    private static final Gson GSON = new Gson();

    private ShopDefinitionLoader() {}

    public static void load(String directory) {
        try {
            Path dir = Path.of(directory);
            if (!Files.exists(dir)) {
                logger.warn("Shop definitions directory not found: {}", directory);
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
                for (Path file : stream) {
                    String json = Files.readString(file);
                    Type listType = new TypeToken<List<ShopDefinition>>() {}.getType();
                    List<ShopDefinition> shops = GSON.fromJson(json, listType);
                    if (shops != null) {
                        for (ShopDefinition shop : shops) {
                            definitions.put(shop.getId(), shop);
                        }
                    }
                }
            }
            logger.info("Loaded {} shop definitions.", definitions.size());
        } catch (IOException e) {
            logger.error("Failed to load shop definitions", e);
        }
    }

    public static ShopDefinition get(String id) {
        return definitions.get(id);
    }

    public static Map<String, ShopDefinition> getAll() {
        return definitions;
    }
}
