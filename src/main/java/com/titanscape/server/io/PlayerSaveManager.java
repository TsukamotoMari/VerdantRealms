package com.titanscape.server.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.entity.player.PlayerRights;
import com.titanscape.server.model.entity.player.SkillType;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.*;

/**
 * Handles saving and loading player data to/from JSON files.
 */
public final class PlayerSaveManager {

    private static final Logger logger = LogManager.getLogger(PlayerSaveManager.class);
    private static final String SAVE_DIR = "data/saves/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private PlayerSaveManager() {}

    public static void save(Player player) {
        try {
            Files.createDirectories(Path.of(SAVE_DIR));
            PlayerData data = toData(player);
            String json = GSON.toJson(data);
            Files.writeString(Path.of(SAVE_DIR + player.getUsername().toLowerCase() + ".json"), json);
            logger.debug("Saved player: {}", player.getUsername());
        } catch (IOException e) {
            logger.error("Failed to save player: {}", player.getUsername(), e);
        }
    }

    public static Player load(String username) {
        Path path = Path.of(SAVE_DIR + username.toLowerCase() + ".json");
        if (!Files.exists(path)) {
            return null;
        }
        try {
            String json = Files.readString(path);
            PlayerData data = GSON.fromJson(json, PlayerData.class);
            return fromData(data);
        } catch (IOException e) {
            logger.error("Failed to load player: {}", username, e);
            return null;
        }
    }

    private static PlayerData toData(Player player) {
        PlayerData data = new PlayerData();
        data.username = player.getUsername();
        data.password = player.getPassword();
        data.rights = player.getRights().name();
        data.x = player.getPosition().getX();
        data.y = player.getPosition().getY();
        data.z = player.getPosition().getZ();
        data.hitpoints = player.getHitpoints();
        data.levels = player.getSkills().getLevels().clone();
        data.experience = player.getSkills().getExperienceArray().clone();

        Item[] inv = player.getInventory().getItems();
        data.inventoryIds = new int[inv.length];
        data.inventoryAmounts = new int[inv.length];
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] != null) {
                data.inventoryIds[i] = inv[i].getId();
                data.inventoryAmounts[i] = inv[i].getAmount();
            } else {
                data.inventoryIds[i] = -1;
                data.inventoryAmounts[i] = 0;
            }
        }

        Item[] bank = player.getBank().getItems();
        data.bankIds = new int[bank.length];
        data.bankAmounts = new int[bank.length];
        for (int i = 0; i < bank.length; i++) {
            if (bank[i] != null) {
                data.bankIds[i] = bank[i].getId();
                data.bankAmounts[i] = bank[i].getAmount();
            } else {
                data.bankIds[i] = -1;
                data.bankAmounts[i] = 0;
            }
        }

        return data;
    }

    private static Player fromData(PlayerData data) {
        Player player = new Player(data.username, data.password);
        player.setRights(PlayerRights.valueOf(data.rights));
        player.setPosition(new Position(data.x, data.y, data.z));

        if (data.levels != null) {
            for (int i = 0; i < data.levels.length && i < SkillType.SKILL_COUNT; i++) {
                player.getSkills().getLevels()[i] = data.levels[i];
            }
        }
        if (data.experience != null) {
            for (int i = 0; i < data.experience.length && i < SkillType.SKILL_COUNT; i++) {
                player.getSkills().getExperienceArray()[i] = data.experience[i];
            }
        }

        if (data.inventoryIds != null) {
            for (int i = 0; i < data.inventoryIds.length; i++) {
                if (data.inventoryIds[i] >= 0) {
                    player.getInventory().set(i, new Item(data.inventoryIds[i], data.inventoryAmounts[i]));
                }
            }
        }

        if (data.bankIds != null) {
            for (int i = 0; i < data.bankIds.length; i++) {
                if (data.bankIds[i] >= 0) {
                    player.getBank().set(i, new Item(data.bankIds[i], data.bankAmounts[i]));
                }
            }
        }

        player.setMaxHitpoints(player.getSkills().getLevel(SkillType.HITPOINTS));
        if (data.hitpoints > 0) {
            player.setHitpoints(data.hitpoints);
        } else {
            player.setHitpoints(player.getMaxHitpoints());
        }

        return player;
    }

    private static class PlayerData {
        String username;
        String password;
        String rights;
        int x, y, z;
        int hitpoints;
        int[] levels;
        double[] experience;
        int[] inventoryIds;
        int[] inventoryAmounts;
        int[] bankIds;
        int[] bankAmounts;
    }
}
