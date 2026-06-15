package com.titanscape.server.model.entity.player;

import com.titanscape.server.model.item.Item;

import java.util.Arrays;
import java.util.Optional;

/**
 * A container for items (inventory, bank, equipment, etc.).
 */
public final class Inventory {

    public static final int INVENTORY_SIZE = 28;
    public static final int BANK_SIZE = 800;
    public static final int EQUIPMENT_SIZE = 14;

    private final Item[] items;
    private final int capacity;

    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new Item[capacity];
    }

    public boolean add(Item item) {
        for (int i = 0; i < capacity; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
            if (items[i].isStackable() && items[i].getId() == item.getId()) {
                items[i] = new Item(item.getId(), items[i].getAmount() + item.getAmount());
                return true;
            }
        }
        return false;
    }

    public boolean remove(int itemId) {
        return remove(itemId, 1);
    }

    public boolean remove(int itemId, int amount) {
        for (int i = 0; i < capacity; i++) {
            if (items[i] != null && items[i].getId() == itemId) {
                if (items[i].getAmount() > amount) {
                    items[i] = new Item(itemId, items[i].getAmount() - amount);
                } else {
                    items[i] = null;
                }
                return true;
            }
        }
        return false;
    }

    public boolean contains(int itemId) {
        return Arrays.stream(items).anyMatch(item -> item != null && item.getId() == itemId);
    }

    public int countOf(int itemId) {
        return Arrays.stream(items)
                .filter(item -> item != null && item.getId() == itemId)
                .mapToInt(Item::getAmount)
                .sum();
    }

    public Optional<Item> get(int slot) {
        if (slot < 0 || slot >= capacity) return Optional.empty();
        return Optional.ofNullable(items[slot]);
    }

    public void set(int slot, Item item) {
        if (slot >= 0 && slot < capacity) {
            items[slot] = item;
        }
    }

    public void clear() {
        Arrays.fill(items, null);
    }

    public boolean isFull() {
        return Arrays.stream(items).noneMatch(java.util.Objects::isNull);
    }

    public int freeSlots() {
        return (int) Arrays.stream(items).filter(java.util.Objects::isNull).count();
    }

    public Item[] getItems() {
        return items;
    }

    public int getCapacity() {
        return capacity;
    }
}
