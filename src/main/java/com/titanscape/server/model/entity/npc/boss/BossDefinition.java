package com.titanscape.server.model.entity.npc.boss;

/**
 * Definition data for a custom boss loaded from JSON.
 */
public final class BossDefinition {

    private int id;
    private String name;
    private String title;
    private String description;
    private int hitpoints;
    private int combatLevel;
    private int maxHit;
    private int attackSpeed;
    private int attackRange;
    private int specialCooldown = 15;
    private String attackStyle;
    private int spawnX;
    private int spawnY;
    private int spawnZ;
    private String[] phases;
    private String[] specialAttacks;
    private String[] dropTableIds;
    private String areaId;
    private int respawnTicks = 100;
    private boolean instanced;
    private int maxPlayers = 1;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getHitpoints() { return hitpoints; }
    public void setHitpoints(int hitpoints) { this.hitpoints = hitpoints; }

    public int getCombatLevel() { return combatLevel; }
    public void setCombatLevel(int combatLevel) { this.combatLevel = combatLevel; }

    public int getMaxHit() { return maxHit; }
    public void setMaxHit(int maxHit) { this.maxHit = maxHit; }

    public int getAttackSpeed() { return attackSpeed; }
    public void setAttackSpeed(int attackSpeed) { this.attackSpeed = attackSpeed; }

    public int getAttackRange() { return attackRange; }
    public void setAttackRange(int attackRange) { this.attackRange = attackRange; }

    public int getSpecialCooldown() { return specialCooldown; }
    public void setSpecialCooldown(int specialCooldown) { this.specialCooldown = specialCooldown; }

    public String getAttackStyle() { return attackStyle; }
    public void setAttackStyle(String attackStyle) { this.attackStyle = attackStyle; }

    public int getSpawnX() { return spawnX; }
    public void setSpawnX(int spawnX) { this.spawnX = spawnX; }

    public int getSpawnY() { return spawnY; }
    public void setSpawnY(int spawnY) { this.spawnY = spawnY; }

    public int getSpawnZ() { return spawnZ; }
    public void setSpawnZ(int spawnZ) { this.spawnZ = spawnZ; }

    public String[] getPhases() { return phases; }
    public void setPhases(String[] phases) { this.phases = phases; }

    public String[] getSpecialAttacks() { return specialAttacks; }
    public void setSpecialAttacks(String[] specialAttacks) { this.specialAttacks = specialAttacks; }

    public String[] getDropTableIds() { return dropTableIds; }
    public void setDropTableIds(String[] dropTableIds) { this.dropTableIds = dropTableIds; }

    public String getAreaId() { return areaId; }
    public void setAreaId(String areaId) { this.areaId = areaId; }

    public int getRespawnTicks() { return respawnTicks; }
    public void setRespawnTicks(int respawnTicks) { this.respawnTicks = respawnTicks; }

    public boolean isInstanced() { return instanced; }
    public void setInstanced(boolean instanced) { this.instanced = instanced; }

    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }
}
