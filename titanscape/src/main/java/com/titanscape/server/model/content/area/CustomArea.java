package com.titanscape.server.model.content.area;

import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.world.Position;

/**
 * Represents a loaded custom area in the game world.
 */
public final class CustomArea {

    private final AreaDefinition definition;

    public CustomArea(AreaDefinition definition) {
        this.definition = definition;
    }

    public boolean contains(Position position) {
        return position.getX() >= definition.getMinX()
            && position.getX() <= definition.getMaxX()
            && position.getY() >= definition.getMinY()
            && position.getY() <= definition.getMaxY();
    }

    public boolean canEnter(Player player) {
        if (definition.getLevel() > 0) {
            return player.getSkills().getCombatLevel() >= definition.getLevel();
        }
        return true;
    }

    public void onEnter(Player player) {
        player.sendMessage("You have entered: " + definition.getName());
        if (definition.isMultiCombat()) {
            player.sendMessage("This is a multi-combat zone.");
        }
        if (definition.isPvpEnabled()) {
            player.sendMessage("Warning: PvP is enabled here!");
        }
    }

    public AreaDefinition getDefinition() {
        return definition;
    }

    public String getId() {
        return definition.getId();
    }

    public String getName() {
        return definition.getName();
    }
}
