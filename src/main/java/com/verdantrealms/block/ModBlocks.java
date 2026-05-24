package com.verdantrealms.block;

import com.verdantrealms.VerdantRealms;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(VerdantRealms.MOD_ID);

    // Crystal blocks
    public static final DeferredBlock<Block> AMETHYST_CRYSTAL_BLOCK = BLOCKS.registerSimpleBlock("amethyst_crystal_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST).lightLevel(state -> 12));

    public static final DeferredBlock<Block> AMBER_CRYSTAL_BLOCK = BLOCKS.registerSimpleBlock("amber_crystal_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST).lightLevel(state -> 10));

    public static final DeferredBlock<Block> MOONSTONE_BLOCK = BLOCKS.registerSimpleBlock("moonstone_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_BLOCK).sound(SoundType.AMETHYST).lightLevel(state -> 14));

    // Wood types
    public static final DeferredBlock<Block> ELDERWOOD_LOG = BLOCKS.registerSimpleBlock("elderwood_log",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).sound(SoundType.WOOD));

    public static final DeferredBlock<Block> ELDERWOOD_PLANKS = BLOCKS.registerSimpleBlock("elderwood_planks",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).sound(SoundType.WOOD));

    public static final DeferredBlock<Block> ELDERWOOD_LEAVES = BLOCKS.registerSimpleBlock("elderwood_leaves",
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).sound(SoundType.GRASS).lightLevel(state -> 6));

    public static final DeferredBlock<Block> STARWOOD_LOG = BLOCKS.registerSimpleBlock("starwood_log",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LOG).sound(SoundType.WOOD));

    public static final DeferredBlock<Block> STARWOOD_LEAVES = BLOCKS.registerSimpleBlock("starwood_leaves",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DARK_OAK_LEAVES).sound(SoundType.GRASS).lightLevel(state -> 8));

    public static final DeferredBlock<Block> ASHENWOOD_LOG = BLOCKS.registerSimpleBlock("ashenwood_log",
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG).sound(SoundType.WOOD));

    public static final DeferredBlock<Block> ASHENWOOD_LEAVES = BLOCKS.registerSimpleBlock("ashenwood_leaves",
            BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES).sound(SoundType.GRASS));

    // Foliage & Plants
    public static final DeferredBlock<Block> LUMINOUS_MOSS = BLOCKS.registerSimpleBlock("luminous_moss",
            BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).sound(SoundType.MOSS).lightLevel(state -> 9));

    public static final DeferredBlock<Block> GLOWING_VINES = BLOCKS.registerSimpleBlock("glowing_vines",
            BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).sound(SoundType.VINE).lightLevel(state -> 7));

    public static final DeferredBlock<Block> CHERRY_BLOSSOM_LEAVES = BLOCKS.registerSimpleBlock("cherry_blossom_leaves",
            BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES).sound(SoundType.GRASS).lightLevel(state -> 3));

    public static final DeferredBlock<Block> LAVENDER_BUSH = BLOCKS.registerSimpleBlock("lavender_bush",
            BlockBehaviour.Properties.ofFullCopy(Blocks.LILAC).sound(SoundType.GRASS));

    public static final DeferredBlock<Block> GIANT_LILY_PAD = BLOCKS.registerSimpleBlock("giant_lily_pad",
            BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).sound(SoundType.LILY_PAD));

    public static final DeferredBlock<Block> FIRE_BLOSSOM = BLOCKS.registerSimpleBlock("fire_blossom",
            BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).sound(SoundType.GRASS).lightLevel(state -> 5));

    public static final DeferredBlock<Block> FROST_FLOWER = BLOCKS.registerSimpleBlock("frost_flower",
            BlockBehaviour.Properties.ofFullCopy(Blocks.BLUE_ORCHID).sound(SoundType.GRASS));

    public static final DeferredBlock<Block> VOID_ROOTS = BLOCKS.registerSimpleBlock("void_roots",
            BlockBehaviour.Properties.ofFullCopy(Blocks.HANGING_ROOTS).sound(SoundType.ROOTS).lightLevel(state -> 4));

    // Edible plants
    public static final DeferredBlock<Block> WILD_BERRY_BUSH = BLOCKS.registerSimpleBlock("wild_berry_bush",
            BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).sound(SoundType.SWEET_BERRY_BUSH));

    public static final DeferredBlock<Block> GLOW_FRUIT_BUSH = BLOCKS.registerSimpleBlock("glow_fruit_bush",
            BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).sound(SoundType.SWEET_BERRY_BUSH).lightLevel(state -> 6));

    public static final DeferredBlock<Block> STAR_ROOT_CROP = BLOCKS.registerSimpleBlock("star_root_crop",
            BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS).sound(SoundType.CROP));

    public static final DeferredBlock<Block> MOON_WHEAT_CROP = BLOCKS.registerSimpleBlock("moon_wheat_crop",
            BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT).sound(SoundType.CROP).lightLevel(state -> 4));

    // Terrain
    public static final DeferredBlock<Block> VOLCANIC_STONE = BLOCKS.registerSimpleBlock("volcanic_stone",
            BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).sound(SoundType.STONE));

    public static final DeferredBlock<Block> GLACIAL_STONE = BLOCKS.registerSimpleBlock("glacial_stone",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).sound(SoundType.STONE));

    public static final DeferredBlock<Block> FLOATING_GRASS = BLOCKS.registerSimpleBlock("floating_grass",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK).sound(SoundType.GRASS));

    public static final DeferredBlock<Block> CORRUPTED_EARTH = BLOCKS.registerSimpleBlock("corrupted_earth",
            BlockBehaviour.Properties.ofFullCopy(Blocks.MYCELIUM).sound(SoundType.GRASS).lightLevel(state -> 2));
}
