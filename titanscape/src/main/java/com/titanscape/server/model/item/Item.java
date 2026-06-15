package com.titanscape.server.model.item;

import com.titanscape.server.definition.ItemDefinitionLoader;

/**
 * Represents an item instance with an ID and amount.
 */
public final class Item {

    private final int id;
    private final int amount;

    public Item(int id) {
        this(id, 1);
    }

    public Item(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        ItemDefinition def = ItemDefinitionLoader.get(id);
        return def != null ? def.getName() : "Unknown";
    }

    public boolean isStackable() {
        ItemDefinition def = ItemDefinitionLoader.get(id);
        return def != null && def.isStackable();
    }

    public boolean isTradeable() {
        ItemDefinition def = ItemDefinitionLoader.get(id);
        return def != null && def.isTradeable();
    }

    @Override
    public String toString() {
        return String.format("Item[id=%d, name=%s, amount=%d]", id, getName(), amount);
    }
}
