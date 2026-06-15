package com.titanscape.server.model.item;

/**
 * Static definition data for an item loaded from JSON.
 */
public final class ItemDefinition {

    private int id;
    private String name;
    private String description;
    private boolean stackable;
    private boolean tradeable;
    private boolean noted;
    private int value;
    private int equipmentSlot = -1;
    private int attackBonus;
    private int strengthBonus;
    private int defenceBonus;
    private int rangedBonus;
    private int magicBonus;
    private int prayerBonus;
    private int levelRequirement;
    private String skillRequirement;
    private String specialEffect;
    private String rarity;
    private boolean custom;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isStackable() { return stackable; }
    public void setStackable(boolean stackable) { this.stackable = stackable; }

    public boolean isTradeable() { return tradeable; }
    public void setTradeable(boolean tradeable) { this.tradeable = tradeable; }

    public boolean isNoted() { return noted; }
    public void setNoted(boolean noted) { this.noted = noted; }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public int getEquipmentSlot() { return equipmentSlot; }
    public void setEquipmentSlot(int equipmentSlot) { this.equipmentSlot = equipmentSlot; }

    public int getAttackBonus() { return attackBonus; }
    public void setAttackBonus(int attackBonus) { this.attackBonus = attackBonus; }

    public int getStrengthBonus() { return strengthBonus; }
    public void setStrengthBonus(int strengthBonus) { this.strengthBonus = strengthBonus; }

    public int getDefenceBonus() { return defenceBonus; }
    public void setDefenceBonus(int defenceBonus) { this.defenceBonus = defenceBonus; }

    public int getRangedBonus() { return rangedBonus; }
    public void setRangedBonus(int rangedBonus) { this.rangedBonus = rangedBonus; }

    public int getMagicBonus() { return magicBonus; }
    public void setMagicBonus(int magicBonus) { this.magicBonus = magicBonus; }

    public int getPrayerBonus() { return prayerBonus; }
    public void setPrayerBonus(int prayerBonus) { this.prayerBonus = prayerBonus; }

    public int getLevelRequirement() { return levelRequirement; }
    public void setLevelRequirement(int levelRequirement) { this.levelRequirement = levelRequirement; }

    public String getSkillRequirement() { return skillRequirement; }
    public void setSkillRequirement(String skillRequirement) { this.skillRequirement = skillRequirement; }

    public String getSpecialEffect() { return specialEffect; }
    public void setSpecialEffect(String specialEffect) { this.specialEffect = specialEffect; }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }

    public boolean isCustom() { return custom; }
    public void setCustom(boolean custom) { this.custom = custom; }
}
