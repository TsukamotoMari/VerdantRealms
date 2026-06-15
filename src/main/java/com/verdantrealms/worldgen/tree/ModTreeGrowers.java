package com.verdantrealms.worldgen.tree;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.worldgen.feature.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower ELDERWOOD = new TreeGrower(
        "elderwood",
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.ELDERWOOD_TREE),
        Optional.empty()
    );

    public static final TreeGrower STARWOOD = new TreeGrower(
        "starwood",
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.STARWOOD_TREE),
        Optional.empty()
    );

    public static final TreeGrower ASHENWOOD = new TreeGrower(
        "ashenwood",
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.ASHENWOOD_TREE),
        Optional.empty()
    );

    public static final TreeGrower CHERRY_BLOSSOM = new TreeGrower(
        "cherry_blossom",
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.CHERRY_BLOSSOM_TREE),
        Optional.empty()
    );

    // Ultra-rare Tree of Life - 0.5% chance
    public static final TreeGrower TREE_OF_LIFE = new TreeGrower(
        "tree_of_life",
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.TREE_OF_LIFE),
        Optional.empty()
    );

    public static void register() {
        VerdantRealms.LOGGER.info("Tree growers registered");
    }
}
