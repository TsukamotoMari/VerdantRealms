package com.titanscape.server.model.content.area;

/**
 * Defines a custom area in the game world.
 */
public final class AreaDefinition {

    private String id;
    private String name;
    private String description;
    private int minX, minY, maxX, maxY;
    private int level;
    private boolean multiCombat;
    private boolean pvpEnabled;
    private boolean safeZone;
    private NpcSpawn[] npcSpawns;
    private String teleportCommand;
    private int teleportX, teleportY, teleportZ;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getMinX() { return minX; }
    public void setMinX(int minX) { this.minX = minX; }
    public int getMinY() { return minY; }
    public void setMinY(int minY) { this.minY = minY; }
    public int getMaxX() { return maxX; }
    public void setMaxX(int maxX) { this.maxX = maxX; }
    public int getMaxY() { return maxY; }
    public void setMaxY(int maxY) { this.maxY = maxY; }
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public boolean isMultiCombat() { return multiCombat; }
    public void setMultiCombat(boolean multiCombat) { this.multiCombat = multiCombat; }
    public boolean isPvpEnabled() { return pvpEnabled; }
    public void setPvpEnabled(boolean pvpEnabled) { this.pvpEnabled = pvpEnabled; }
    public boolean isSafeZone() { return safeZone; }
    public void setSafeZone(boolean safeZone) { this.safeZone = safeZone; }
    public NpcSpawn[] getNpcSpawns() { return npcSpawns; }
    public void setNpcSpawns(NpcSpawn[] npcSpawns) { this.npcSpawns = npcSpawns; }
    public String getTeleportCommand() { return teleportCommand; }
    public void setTeleportCommand(String teleportCommand) { this.teleportCommand = teleportCommand; }
    public int getTeleportX() { return teleportX; }
    public void setTeleportX(int teleportX) { this.teleportX = teleportX; }
    public int getTeleportY() { return teleportY; }
    public void setTeleportY(int teleportY) { this.teleportY = teleportY; }
    public int getTeleportZ() { return teleportZ; }
    public void setTeleportZ(int teleportZ) { this.teleportZ = teleportZ; }

    public static class NpcSpawn {
        public int npcId;
        public int x;
        public int y;
        public int z;
    }
}
