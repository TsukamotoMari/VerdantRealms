package com.titanscape.server.model.entity.player;

import com.titanscape.server.TitanScape;
import com.titanscape.server.io.PlayerSaveManager;
import com.titanscape.server.model.entity.Entity;
import com.titanscape.server.model.world.Position;
import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a player in the game world.
 */
public final class Player extends Entity {

    private static final Logger logger = LogManager.getLogger(Player.class);

    public static final Position DEFAULT_SPAWN = new Position(3222, 3218);

    private final String username;
    private String password;
    private PlayerRights rights;
    private final Skills skills;
    private final Inventory inventory;
    private final Inventory bank;
    private final Equipment equipment;
    private Channel channel;
    private long lastSave;
    private boolean needsSave;

    public Player(String username, String password) {
        super(DEFAULT_SPAWN, 10);
        this.username = username;
        this.password = password;
        this.rights = PlayerRights.PLAYER;
        this.skills = new Skills();
        this.inventory = new Inventory(Inventory.INVENTORY_SIZE);
        this.bank = new Inventory(Inventory.BANK_SIZE);
        this.equipment = new Equipment();
        setMaxHitpoints(skills.getLevel(SkillType.HITPOINTS));
        setHitpoints(getMaxHitpoints());
    }

    @Override
    public void process() {
        if (System.currentTimeMillis() - lastSave > 60_000 && needsSave) {
            save();
        }
    }

    @Override
    protected void onDeath() {
        logger.info("{} has died!", username);
        setPosition(DEFAULT_SPAWN);
        setHitpoints(getMaxHitpoints());
        setDead(false);
        sendMessage("Oh dear, you have died!");
    }

    public void sendMessage(String message) {
        if (channel != null && channel.isActive()) {
            // In a full implementation this would encode a server message packet
            logger.debug("[{}] Server message: {}", username, message);
        }
    }

    public void save() {
        PlayerSaveManager.save(this);
        lastSave = System.currentTimeMillis();
        needsSave = false;
    }

    public void markDirty() {
        needsSave = true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PlayerRights getRights() {
        return rights;
    }

    public void setRights(PlayerRights rights) {
        this.rights = rights;
    }

    public Skills getSkills() {
        return skills;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Inventory getBank() {
        return bank;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
