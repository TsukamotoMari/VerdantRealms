package com.titanscape.server.model.entity.player;

/**
 * All skill types available in TitanScape.
 */
public enum SkillType {
    ATTACK(0, "Attack"),
    DEFENCE(1, "Defence"),
    STRENGTH(2, "Strength"),
    HITPOINTS(3, "Hitpoints"),
    RANGED(4, "Ranged"),
    PRAYER(5, "Prayer"),
    MAGIC(6, "Magic"),
    COOKING(7, "Cooking"),
    WOODCUTTING(8, "Woodcutting"),
    FLETCHING(9, "Fletching"),
    FISHING(10, "Fishing"),
    FIREMAKING(11, "Firemaking"),
    CRAFTING(12, "Crafting"),
    SMITHING(13, "Smithing"),
    MINING(14, "Mining"),
    HERBLORE(15, "Herblore"),
    AGILITY(16, "Agility"),
    THIEVING(17, "Thieving"),
    SLAYER(18, "Slayer"),
    FARMING(19, "Farming"),
    RUNECRAFTING(20, "Runecrafting"),
    CONSTRUCTION(21, "Construction"),
    HUNTER(22, "Hunter"),

    // Custom skills unique to TitanScape
    SUMMONING(23, "Summoning"),
    DUNGEONEERING(24, "Dungeoneering"),
    TITAN_FORGING(25, "Titan Forging");

    private final int id;
    private final String name;

    SkillType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static final int SKILL_COUNT = values().length;
}
