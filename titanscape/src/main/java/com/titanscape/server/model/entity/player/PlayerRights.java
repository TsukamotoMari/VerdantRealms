package com.titanscape.server.model.entity.player;

/**
 * Player permission levels.
 */
public enum PlayerRights {
    PLAYER(0, "Player"),
    MODERATOR(1, "Moderator"),
    ADMINISTRATOR(2, "Administrator"),
    OWNER(3, "Owner"),
    DEVELOPER(4, "Developer");

    private final int value;
    private final String name;

    PlayerRights(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean isStaff() {
        return value >= MODERATOR.value;
    }

    public boolean isAdmin() {
        return value >= ADMINISTRATOR.value;
    }
}
