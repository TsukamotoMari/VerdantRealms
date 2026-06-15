package com.verdantrealms.item;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VerdantRealms.MOD_ID);

    // Food items
    public static final DeferredItem<Item> WILD_BERRIES = ITEMS.registerSimpleItem("wild_berries", foodProperties(3, 0.3f));
    public static final DeferredItem<Item> GLOW_FRUIT = ITEMS.registerSimpleItem("glow_fruit", foodProperties(5, 0.5f));
    public static final DeferredItem<Item> STAR_ROOT = ITEMS.registerSimpleItem("star_root", foodProperties(4, 0.4f));
    public static final DeferredItem<Item> MOON_BREAD = ITEMS.registerSimpleItem("moon_bread", foodProperties(7, 0.7f));
    public static final DeferredItem<Item> LAVENDER_TEA = ITEMS.registerSimpleItem("lavender_tea", alwaysEdibleFoodProperties(2, 0.2f));

    public static final DeferredItem<Item> CRYSTAL_SHARD = ITEMS.registerSimpleItem("crystal_shard", new Item.Properties());
    public static final DeferredItem<Item> ANCIENT_SEED = ITEMS.registerSimpleItem("ancient_seed", new Item.Properties());

    // Block items (auto-generated)
    public static final DeferredItem<BlockItem> AMETHYST_CRYSTAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("amethyst_crystal_block", ModBlocks.AMETHYST_CRYSTAL_BLOCK);
    public static final DeferredItem<BlockItem> AMBER_CRYSTAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("amber_crystal_block", ModBlocks.AMBER_CRYSTAL_BLOCK);
    public static final DeferredItem<BlockItem> MOONSTONE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("moonstone_block", ModBlocks.MOONSTONE_BLOCK);
    public static final DeferredItem<BlockItem> ELDERWOOD_LOG_ITEM = ITEMS.registerSimpleBlockItem("elderwood_log", ModBlocks.ELDERWOOD_LOG);
    public static final DeferredItem<BlockItem> ELDERWOOD_PLANKS_ITEM = ITEMS.registerSimpleBlockItem("elderwood_planks", ModBlocks.ELDERWOOD_PLANKS);
    public static final DeferredItem<BlockItem> ELDERWOOD_LEAVES_ITEM = ITEMS.registerSimpleBlockItem("elderwood_leaves", ModBlocks.ELDERWOOD_LEAVES);
    public static final DeferredItem<BlockItem> STARWOOD_LOG_ITEM = ITEMS.registerSimpleBlockItem("starwood_log", ModBlocks.STARWOOD_LOG);
    public static final DeferredItem<BlockItem> STARWOOD_LEAVES_ITEM = ITEMS.registerSimpleBlockItem("starwood_leaves", ModBlocks.STARWOOD_LEAVES);
    public static final DeferredItem<BlockItem> ASHENWOOD_LOG_ITEM = ITEMS.registerSimpleBlockItem("ashenwood_log", ModBlocks.ASHENWOOD_LOG);
    public static final DeferredItem<BlockItem> ASHENWOOD_LEAVES_ITEM = ITEMS.registerSimpleBlockItem("ashenwood_leaves", ModBlocks.ASHENWOOD_LEAVES);
    public static final DeferredItem<BlockItem> LUMINOUS_MOSS_ITEM = ITEMS.registerSimpleBlockItem("luminous_moss", ModBlocks.LUMINOUS_MOSS);
    public static final DeferredItem<BlockItem> CHERRY_BLOSSOM_LEAVES_ITEM = ITEMS.registerSimpleBlockItem("cherry_blossom_leaves", ModBlocks.CHERRY_BLOSSOM_LEAVES);
    public static final DeferredItem<BlockItem> LAVENDER_BUSH_ITEM = ITEMS.registerSimpleBlockItem("lavender_bush", ModBlocks.LAVENDER_BUSH);
    public static final DeferredItem<BlockItem> GIANT_LILY_PAD_ITEM = ITEMS.registerSimpleBlockItem("giant_lily_pad", ModBlocks.GIANT_LILY_PAD);
    public static final DeferredItem<BlockItem> FIRE_BLOSSOM_ITEM = ITEMS.registerSimpleBlockItem("fire_blossom", ModBlocks.FIRE_BLOSSOM);
    public static final DeferredItem<BlockItem> FROST_FLOWER_ITEM = ITEMS.registerSimpleBlockItem("frost_flower", ModBlocks.FROST_FLOWER);
    public static final DeferredItem<BlockItem> VOID_ROOTS_ITEM = ITEMS.registerSimpleBlockItem("void_roots", ModBlocks.VOID_ROOTS);
    public static final DeferredItem<BlockItem> VOLCANIC_STONE_ITEM = ITEMS.registerSimpleBlockItem("volcanic_stone", ModBlocks.VOLCANIC_STONE);
    public static final DeferredItem<BlockItem> GLACIAL_STONE_ITEM = ITEMS.registerSimpleBlockItem("glacial_stone", ModBlocks.GLACIAL_STONE);
    public static final DeferredItem<BlockItem> FLOATING_GRASS_ITEM = ITEMS.registerSimpleBlockItem("floating_grass", ModBlocks.FLOATING_GRASS);
    public static final DeferredItem<BlockItem> CORRUPTED_EARTH_ITEM = ITEMS.registerSimpleBlockItem("corrupted_earth", ModBlocks.CORRUPTED_EARTH);

    private static Item.Properties foodProperties(int nutrition, float saturation) {
        return new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(nutrition).saturationModifier(saturation).build());
    }

    private static Item.Properties alwaysEdibleFoodProperties(int nutrition, float saturation) {
        return new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(nutrition).saturationModifier(saturation).alwaysEdible().build());
    }
}
