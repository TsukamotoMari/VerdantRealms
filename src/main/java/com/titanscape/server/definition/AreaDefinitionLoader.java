package com.titanscape.server.definition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.titanscape.server.model.content.area.AreaDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AreaDefinitionLoader {

    private static final Logger logger = LogManager.getLogger(AreaDefinitionLoader.class);
    private static final Map<String, AreaDefinition> definitions = new HashMap<>();
    private static final Gson GSON = new Gson();

    private AreaDefinitionLoader() {}

    public static void load(String directory) {
        try {
            Path dir = Path.of(directory);
            if (!Files.exists(dir)) {
                logger.warn("Area definitions directory not found: {}", directory);
                return;
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.json")) {
                for (Path file : stream) {
                    String json = Files.readString(file);
                    Type listType = new TypeToken<List<AreaDefinition>>() {}.getType();
                    List<AreaDefinition> areas = GSON.fromJson(json, listType);
                    if (areas != null) {
                        for (AreaDefinition area : areas) {
                            definitions.put(area.getId(), area);
                        }
                    }
                }
            }
            logger.info("Loaded {} area definitions.", definitions.size());
        } catch (IOException e) {
            logger.error("Failed to load area definitions", e);
        }
    }

    public static AreaDefinition get(String id) {
        return definitions.get(id);
    }

    public static Map<String, AreaDefinition> getAll() {
        return definitions;
    }
}
