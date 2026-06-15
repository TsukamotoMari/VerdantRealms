package com.verdantrealms.worldgen.tree;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.util.RegistryHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower ELDERWOOD = createGrower("elderwood");
    public static final TreeGrower STARWOOD = createGrower("starwood");
    public static final TreeGrower ASHENWOOD = createGrower("ashenwood");
    public static final TreeGrower CHERRY_BLOSSOM = createGrower("cherry_blossom");
    public static final TreeGrower TREE_OF_LIFE = createGrower("tree_of_life");

    private static TreeGrower createGrower(String name) {
        ResourceKey<ConfiguredFeature<?, ?>> featureKey = RegistryHelper.key(Registries.CONFIGURED_FEATURE, name);
        return new TreeGrower(name, Optional.empty(), Optional.of(featureKey), Optional.empty());
    }

    public static void register() {
        VerdantRealms.LOGGER.info("Tree growers registered");
    }
}
