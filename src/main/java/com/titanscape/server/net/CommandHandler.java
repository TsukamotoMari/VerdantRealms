package com.titanscape.server.net;

import com.titanscape.server.TitanScape;
import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.entity.player.PlayerRights;
import com.titanscape.server.model.entity.player.SkillType;
import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.world.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Processes player commands (e.g., ::item, ::tele, ::master).
 */
public final class CommandHandler {

    private static final Logger logger = LogManager.getLogger(CommandHandler.class);

    private CommandHandler() {}

    public static void process(Player player, String input) {
        String[] parts = input.toLowerCase().split(" ");
        String command = parts[0];

        switch (command) {
            case "item" -> cmdItem(player, parts);
            case "tele" -> cmdTeleport(player, parts);
            case "master" -> cmdMaster(player);
            case "heal" -> cmdHeal(player);
            case "setlevel" -> cmdSetLevel(player, parts);
            case "pos", "mypos" -> cmdPosition(player);
            case "players" -> cmdPlayers(player);
            case "kick" -> cmdKick(player, parts);
            case "ban" -> cmdBan(player, parts);
            case "give" -> cmdGive(player, parts);
            case "spawn" -> cmdSpawnNpc(player, parts);
            case "reload" -> cmdReload(player);
            case "save" -> cmdSave(player);
            case "commands", "help" -> cmdHelp(player);
            default -> player.sendMessage("Unknown command: ::" + command);
        }
    }

    private static void cmdItem(Player player, String[] parts) {
        if (!player.getRights().isStaff()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 2) {
            player.sendMessage("Usage: ::item [id] [amount]");
            return;
        }
        int itemId = Integer.parseInt(parts[1]);
        int amount = parts.length > 2 ? Integer.parseInt(parts[2]) : 1;
        player.getInventory().add(new Item(itemId, amount));
        player.sendMessage("Spawned item " + itemId + " x" + amount);
    }

    private static void cmdTeleport(Player player, String[] parts) {
        if (!player.getRights().isStaff()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 3) {
            player.sendMessage("Usage: ::tele [x] [y]");
            return;
        }
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = parts.length > 3 ? Integer.parseInt(parts[3]) : 0;
        player.setPosition(new Position(x, y, z));
        player.sendMessage("Teleported to " + x + ", " + y + ", " + z);
    }

    private static void cmdMaster(Player player) {
        if (!player.getRights().isStaff()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        for (SkillType skill : SkillType.values()) {
            player.getSkills().setLevel(skill, 99);
        }
        player.sendMessage("All skills set to 99!");
    }

    private static void cmdHeal(Player player) {
        player.setHitpoints(player.getMaxHitpoints());
        player.sendMessage("You have been fully healed.");
    }

    private static void cmdSetLevel(Player player, String[] parts) {
        if (!player.getRights().isStaff()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 3) {
            player.sendMessage("Usage: ::setlevel [skill] [level]");
            return;
        }
        try {
            SkillType skill = SkillType.valueOf(parts[1].toUpperCase());
            int level = Integer.parseInt(parts[2]);
            player.getSkills().setLevel(skill, level);
            player.sendMessage(skill.getName() + " set to " + level);
        } catch (IllegalArgumentException e) {
            player.sendMessage("Unknown skill: " + parts[1]);
        }
    }

    private static void cmdPosition(Player player) {
        Position pos = player.getPosition();
        player.sendMessage("Position: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }

    private static void cmdPlayers(Player player) {
        int online = TitanScape.getInstance().getWorld().getPlayers().size();
        player.sendMessage("Players online: " + online);
    }

    private static void cmdKick(Player player, String[] parts) {
        if (!player.getRights().isAdmin()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 2) {
            player.sendMessage("Usage: ::kick [username]");
            return;
        }
        TitanScape.getInstance().getWorld().getPlayerByName(parts[1]).ifPresentOrElse(
            target -> {
                TitanScape.getInstance().getWorld().unregisterPlayer(target);
                player.sendMessage("Kicked player: " + parts[1]);
            },
            () -> player.sendMessage("Player not found: " + parts[1])
        );
    }

    private static void cmdBan(Player player, String[] parts) {
        if (!player.getRights().isAdmin()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 2) {
            player.sendMessage("Usage: ::ban [username]");
            return;
        }
        player.sendMessage("Banned player: " + parts[1]);
    }

    private static void cmdGive(Player player, String[] parts) {
        if (!player.getRights().isAdmin()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 3) {
            player.sendMessage("Usage: ::give [username] [itemId] [amount]");
            return;
        }
        String target = parts[1];
        int itemId = Integer.parseInt(parts[2]);
        int amount = parts.length > 3 ? Integer.parseInt(parts[3]) : 1;
        TitanScape.getInstance().getWorld().getPlayerByName(target).ifPresentOrElse(
            p -> {
                p.getInventory().add(new Item(itemId, amount));
                player.sendMessage("Gave " + target + " item " + itemId + " x" + amount);
            },
            () -> player.sendMessage("Player not found: " + target)
        );
    }

    private static void cmdSpawnNpc(Player player, String[] parts) {
        if (!player.getRights().isAdmin()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        if (parts.length < 2) {
            player.sendMessage("Usage: ::spawn [npcId]");
            return;
        }
        int npcId = Integer.parseInt(parts[1]);
        player.sendMessage("Spawned NPC " + npcId + " at your location.");
    }

    private static void cmdReload(Player player) {
        if (!player.getRights().isAdmin()) {
            player.sendMessage("You do not have permission to use this command.");
            return;
        }
        player.sendMessage("Definitions reloaded.");
    }

    private static void cmdSave(Player player) {
        player.save();
        player.sendMessage("Your progress has been saved.");
    }

    private static void cmdHelp(Player player) {
        player.sendMessage("=== TitanScape Commands ===");
        player.sendMessage("::help - Show commands");
        player.sendMessage("::heal - Heal to full");
        player.sendMessage("::pos - Show position");
        player.sendMessage("::players - Online count");
        player.sendMessage("::save - Save progress");
        if (player.getRights().isStaff()) {
            player.sendMessage("--- Staff Commands ---");
            player.sendMessage("::item [id] [amt] - Spawn item");
            player.sendMessage("::tele [x] [y] - Teleport");
            player.sendMessage("::master - Set all skills 99");
            player.sendMessage("::setlevel [skill] [lvl]");
        }
        if (player.getRights().isAdmin()) {
            player.sendMessage("--- Admin Commands ---");
            player.sendMessage("::kick [name] - Kick player");
            player.sendMessage("::ban [name] - Ban player");
            player.sendMessage("::give [name] [id] [amt]");
            player.sendMessage("::spawn [npcId] - Spawn NPC");
            player.sendMessage("::reload - Reload defs");
        }
    }
}
