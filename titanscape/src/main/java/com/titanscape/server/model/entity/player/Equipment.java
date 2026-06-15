package com.titanscape.server.model.entity.player;

import com.titanscape.server.model.item.Item;
import com.titanscape.server.model.item.ItemDefinition;
import com.titanscape.server.definition.ItemDefinitionLoader;

import java.util.Optional;

/**
 * Manages player equipment slots.
 */
public final class Equipment {

    public static final int HELMET = 0;
    public static final int CAPE = 1;
    public static final int AMULET = 2;
    public static final int WEAPON = 3;
    public static final int BODY = 4;
    public static final int SHIELD = 5;
    public static final int LEGS = 7;
    public static final int GLOVES = 9;
    public static final int BOOTS = 10;
    public static final int RING = 12;
    public static final int ARROWS = 13;

    private final Inventory container;

    public Equipment() {
        this.container = new Inventory(Inventory.EQUIPMENT_SIZE);
    }

    public boolean equip(Player player, Item item) {
        ItemDefinition def = ItemDefinitionLoader.get(item.getId());
        if (def == null) return false;

        int slot = def.getEquipmentSlot();
        if (slot < 0) return false;

        Optional<Item> current = container.get(slot);
        current.ifPresent(old -> player.getInventory().add(old));
        container.set(slot, item);
        return true;
    }

    public Optional<Item> unequip(Player player, int slot) {
        Optional<Item> item = container.get(slot);
        if (item.isPresent()) {
            if (player.getInventory().isFull()) {
                return Optional.empty();
            }
            player.getInventory().add(item.get());
            container.set(slot, null);
        }
        return item;
    }

    public Optional<Item> getSlot(int slot) {
        return container.get(slot);
    }

    public int getAttackBonus() {
        int bonus = 0;
        for (int i = 0; i < container.getCapacity(); i++) {
            Optional<Item> item = container.get(i);
            if (item.isPresent()) {
                ItemDefinition def = ItemDefinitionLoader.get(item.get().getId());
                if (def != null) bonus += def.getAttackBonus();
            }
        }
        return bonus;
    }

    public int getStrengthBonus() {
        int bonus = 0;
        for (int i = 0; i < container.getCapacity(); i++) {
            Optional<Item> item = container.get(i);
            if (item.isPresent()) {
                ItemDefinition def = ItemDefinitionLoader.get(item.get().getId());
                if (def != null) bonus += def.getStrengthBonus();
            }
        }
        return bonus;
    }

    public int getDefenceBonus() {
        int bonus = 0;
        for (int i = 0; i < container.getCapacity(); i++) {
            Optional<Item> item = container.get(i);
            if (item.isPresent()) {
                ItemDefinition def = ItemDefinitionLoader.get(item.get().getId());
                if (def != null) bonus += def.getDefenceBonus();
            }
        }
        return bonus;
    }

    public int getRangedBonus() {
        int bonus = 0;
        for (int i = 0; i < container.getCapacity(); i++) {
            Optional<Item> item = container.get(i);
            if (item.isPresent()) {
                ItemDefinition def = ItemDefinitionLoader.get(item.get().getId());
                if (def != null) bonus += def.getRangedBonus();
            }
        }
        return bonus;
    }

    public int getMagicBonus() {
        int bonus = 0;
        for (int i = 0; i < container.getCapacity(); i++) {
            Optional<Item> item = container.get(i);
            if (item.isPresent()) {
                ItemDefinition def = ItemDefinitionLoader.get(item.get().getId());
                if (def != null) bonus += def.getMagicBonus();
            }
        }
        return bonus;
    }

    public Inventory getContainer() {
        return container;
    }
}
