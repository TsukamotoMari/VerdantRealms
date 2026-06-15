package com.titanscape.server.model.combat;

/**
 * Combat attack styles.
 */
public enum CombatStyle {
    MELEE_SLASH("Slash", "melee"),
    MELEE_STAB("Stab", "melee"),
    MELEE_CRUSH("Crush", "melee"),
    RANGED("Ranged", "ranged"),
    MAGIC("Magic", "magic");

    private final String name;
    private final String type;

    CombatStyle(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() { return name; }
    public String getType() { return type; }
}
