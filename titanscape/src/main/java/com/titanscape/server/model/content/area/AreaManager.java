package com.titanscape.server.model.content.area;

import com.titanscape.server.definition.AreaDefinitionLoader;
import com.titanscape.server.model.entity.npc.Npc;
import com.titanscape.server.model.world.Position;
import com.titanscape.server.model.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages all custom areas in the game world.
 */
public final class AreaManager {

    private static final Logger logger = LogManager.getLogger(AreaManager.class);

    private final Map<String, CustomArea> areas = new HashMap<>();

    public void loadAreas() {
        for (var entry : AreaDefinitionLoader.getAll().entrySet()) {
            areas.put(entry.getKey(), new CustomArea(entry.getValue()));
        }
        logger.info("Loaded {} custom areas.", areas.size());
    }

    public void spawnAreaNpcs(World world) {
        for (CustomArea area : areas.values()) {
            AreaDefinition def = area.getDefinition();
            if (def.getNpcSpawns() != null) {
                for (AreaDefinition.NpcSpawn spawn : def.getNpcSpawns()) {
                    Npc npc = new Npc(spawn.npcId, new Position(spawn.x, spawn.y, spawn.z));
                    world.registerNpc(npc);
                }
            }
        }
    }

    public Optional<CustomArea> getAreaAt(Position position) {
        for (CustomArea area : areas.values()) {
            if (area.contains(position)) {
                return Optional.of(area);
            }
        }
        return Optional.empty();
    }

    public Optional<CustomArea> getArea(String id) {
        return Optional.ofNullable(areas.get(id));
    }

    public int getAreaCount() {
        return areas.size();
    }
}
