package com.verdantrealms.worldgen.tree;

import com.verdantrealms.VerdantRealms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower ELDERWOOD = new TreeGrower(
        "elderwood",
        Optional.empty(),
        Optional.of(ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, "elderwood")),
        Optional.empty()
    );

    public static final TreeGrower STARWOOD = new TreeGrower(
        "starwood",
        Optional.empty(),
        Optional.of(ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, "starwood")),
        Optional.empty()
    );

    public static final TreeGrower ASHENWOOD = new TreeGrower(
        "ashenwood",
        Optional.empty(),
        Optional.of(ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, "ashenwood")),
        Optional.empty()
    );

    public static final TreeGrower CHERRY_BLOSSOM = new TreeGrower(
        "cherry_blossom",
        Optional.empty(),
        Optional.of(ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, "cherry_blossom")),
        Optional.empty()
    );

    // Ultra-rare Tree of Life - 0.5% chance
    public static final TreeGrower TREE_OF_LIFE = new TreeGrower(
        "tree_of_life",
        Optional.empty(),
        Optional.of(ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, "tree_of_life")),
        Optional.empty()
    );

    public static void register() {
        VerdantRealms.LOGGER.info("Tree growers registered");
    }
}
