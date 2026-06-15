package com.titanscape.server.model.entity.npc;

/**
 * Static definition data for an NPC loaded from JSON.
 */
public final class NpcDefinition {

    private int id;
    private String name;
    private String description;
    private int hitpoints;
    private int combatLevel;
    private int maxHit;
    private int attackSpeed;
    private int attackRange = 1;
    private boolean aggressive;
    private int wanderRadius = 3;
    private int respawnTicks = 50;
    private String attackStyle;
    private int attackBonus;
    private int defenceBonus;
    private boolean isBoss;
    private String[] dialogue;
    private boolean custom;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

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

    public boolean isAggressive() { return aggressive; }
    public void setAggressive(boolean aggressive) { this.aggressive = aggressive; }

    public int getWanderRadius() { return wanderRadius; }
    public void setWanderRadius(int wanderRadius) { this.wanderRadius = wanderRadius; }

    public int getRespawnTicks() { return respawnTicks; }
    public void setRespawnTicks(int respawnTicks) { this.respawnTicks = respawnTicks; }

    public String getAttackStyle() { return attackStyle; }
    public void setAttackStyle(String attackStyle) { this.attackStyle = attackStyle; }

    public int getAttackBonus() { return attackBonus; }
    public void setAttackBonus(int attackBonus) { this.attackBonus = attackBonus; }

    public int getDefenceBonus() { return defenceBonus; }
    public void setDefenceBonus(int defenceBonus) { this.defenceBonus = defenceBonus; }

    public boolean isBoss() { return isBoss; }
    public void setBoss(boolean boss) { isBoss = boss; }

    public String[] getDialogue() { return dialogue; }
    public void setDialogue(String[] dialogue) { this.dialogue = dialogue; }

    public boolean isCustom() { return custom; }
    public void setCustom(boolean custom) { this.custom = custom; }
}
