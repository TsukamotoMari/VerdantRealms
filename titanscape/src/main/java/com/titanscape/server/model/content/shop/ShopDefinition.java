package com.titanscape.server.model.content.shop;

public final class ShopDefinition {

    private String id;
    private String name;
    private String description;
    private boolean generalStore;
    private ShopItem[] items;
    private String currency;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isGeneralStore() { return generalStore; }
    public void setGeneralStore(boolean generalStore) { this.generalStore = generalStore; }
    public ShopItem[] getItems() { return items; }
    public void setItems(ShopItem[] items) { this.items = items; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public static class ShopItem {
        public int itemId;
        public int stock;
        public int buyPrice;
        public int sellPrice;
    }
}
