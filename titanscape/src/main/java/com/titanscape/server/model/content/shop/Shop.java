package com.titanscape.server.model.content.shop;

import com.titanscape.server.model.entity.player.Player;
import com.titanscape.server.model.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a shop that players can buy from and sell to.
 */
public final class Shop {

    private static final Logger logger = LogManager.getLogger(Shop.class);

    private final ShopDefinition definition;
    private final int[] currentStock;

    public Shop(ShopDefinition definition) {
        this.definition = definition;
        this.currentStock = new int[definition.getItems().length];
        for (int i = 0; i < definition.getItems().length; i++) {
            currentStock[i] = definition.getItems()[i].stock;
        }
    }

    public boolean buy(Player player, int slot, int amount) {
        if (slot < 0 || slot >= definition.getItems().length) return false;

        ShopDefinition.ShopItem shopItem = definition.getItems()[slot];
        if (currentStock[slot] < amount) {
            player.sendMessage("This shop doesn't have enough stock.");
            return false;
        }

        int totalCost = shopItem.buyPrice * amount;
        int currencyId = "coins".equals(definition.getCurrency()) ? 995 : 20010;

        if (player.getInventory().countOf(currencyId) < totalCost) {
            player.sendMessage("You don't have enough to buy this.");
            return false;
        }

        player.getInventory().remove(currencyId, totalCost);
        player.getInventory().add(new Item(shopItem.itemId, amount));
        currentStock[slot] -= amount;

        player.sendMessage("You bought " + amount + "x from " + definition.getName() + ".");
        return true;
    }

    public boolean sell(Player player, int itemId, int amount) {
        if (!player.getInventory().contains(itemId)) return false;

        for (ShopDefinition.ShopItem shopItem : definition.getItems()) {
            if (shopItem.itemId == itemId) {
                int totalValue = shopItem.sellPrice * amount;
                player.getInventory().remove(itemId, amount);
                int currencyId = "coins".equals(definition.getCurrency()) ? 995 : 20010;
                player.getInventory().add(new Item(currencyId, totalValue));
                player.sendMessage("You sold " + amount + "x for " + totalValue + ".");
                return true;
            }
        }
        player.sendMessage("This shop doesn't buy that item.");
        return false;
    }

    public ShopDefinition getDefinition() {
        return definition;
    }
}
