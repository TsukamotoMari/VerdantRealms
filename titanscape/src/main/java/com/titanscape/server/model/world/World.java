package com.titanscape.server.model.world;

import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.entity.npc.Npc;
import com.titanscape.server.model.content.area.CustomArea;
import com.titanscape.server.model.content.area.AreaManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The game world containing all players, NPCs, and areas.
 */
public final class World {

    private static final Logger logger = LogManager.getLogger(World.class);

    public static final int MAX_PLAYERS = 2048;
    public static final int MAX_NPCS = 32768;

    private final EntityList<Player> players = new EntityList<>(MAX_PLAYERS);
    private final EntityList<Npc> npcs = new EntityList<>(MAX_NPCS);
    private final Map<String, Object> globalAttributes = new ConcurrentHashMap<>();
    private final AreaManager areaManager = new AreaManager();

    public void init() {
        logger.info("Initializing world...");
        areaManager.loadAreas();
        areaManager.spawnAreaNpcs(this);
        logger.info("World initialized with {} custom areas.", areaManager.getAreaCount());
    }

    public boolean registerPlayer(Player player) {
        boolean added = players.add(player);
        if (added) {
            logger.info("Player '{}' logged in. Online: {}", player.getUsername(), players.size());
        }
        return added;
    }

    public void unregisterPlayer(Player player) {
        player.save();
        players.remove(player);
        logger.info("Player '{}' logged out. Online: {}", player.getUsername(), players.size());
    }

    public boolean registerNpc(Npc npc) {
        return npcs.add(npc);
    }

    public void unregisterNpc(Npc npc) {
        npcs.remove(npc);
    }

    public EntityList<Player> getPlayers() {
        return players;
    }

    public EntityList<Npc> getNpcs() {
        return npcs;
    }

    public Optional<Player> getPlayerByName(String username) {
        for (Player p : players) {
            if (p != null && p.getUsername().equalsIgnoreCase(username)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public void setAttribute(String key, Object value) {
        globalAttributes.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) globalAttributes.get(key);
    }

    public void saveAll() {
        logger.info("Saving all players...");
        for (Player player : players) {
            if (player != null) {
                player.save();
            }
        }
        logger.info("All players saved.");
    }

    public void process() {
        for (Player player : players) {
            if (player != null) {
                player.process();
            }
        }
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.process();
            }
        }
    }
}
