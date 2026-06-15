package com.verdantrealms.worldgen.feature;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.*;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELDERWOOD_TREE = registerKey("elderwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STARWOOD_TREE = registerKey("starwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHENWOOD_TREE = registerKey("ashenwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BLOSSOM_TREE = registerKey("cherry_blossom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_OF_LIFE = registerKey("tree_of_life");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_MUSHROOM = registerKey("giant_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_SPIKE = registerKey("crystal_spike");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINOUS_VINES = registerKey("luminous_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOATING_ISLAND = registerKey("floating_island");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOLCANIC_VENT = registerKey("volcanic_vent");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROST_SPIRE = registerKey("frost_spire");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_RUINS = registerKey("ancient_ruins");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, name));
    }

    public static void register() {
        VerdantRealms.LOGGER.info("Initializing VerdantRealms configured feature keys (registration handled via data-driven bootstrap)");
        if (ELDERWOOD_TREE == null || TREE_OF_LIFE == null) {
            throw new IllegalStateException("VerdantRealms configured feature resource keys failed to initialize");
        }
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        try {
            registerElderwood(context);
            registerStarwood(context);
            registerAshenwood(context);
            registerCherryBlossom(context);
            registerTreeOfLife(context);
            VerdantRealms.LOGGER.info("All VerdantRealms configured features bootstrapped successfully");
        } catch (Exception e) {
            VerdantRealms.LOGGER.error("Failed to bootstrap VerdantRealms configured features", e);
            throw new RuntimeException("VerdantRealms feature bootstrap failed", e);
        }
    }

    private static void registerElderwood(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, ELDERWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.ELDERWOOD_LOG.get()),
            new FancyTrunkPlacer(8, 12, 5),
            BlockStateProvider.simple(ModBlocks.ELDERWOOD_LEAVES.get()),
            new BlobFoliagePlacer(ConstantInt.of(4), ConstantInt.of(2), 4),
            new TwoLayersFeatureSize(2, 0, 2)
        ).build());
    }

    private static void registerStarwood(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, STARWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.STARWOOD_LOG.get()),
            new DarkOakTrunkPlacer(10, 6, 8),
            BlockStateProvider.simple(ModBlocks.STARWOOD_LEAVES.get()),
            new DarkOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1)),
            new TwoLayersFeatureSize(2, 0, 2)
        ).build());
    }

    private static void registerAshenwood(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, ASHENWOOD_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.ASHENWOOD_LOG.get()),
            new StraightTrunkPlacer(6, 3, 2),
            BlockStateProvider.simple(ModBlocks.ASHENWOOD_LEAVES.get()),
            new MegaPineFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(4)),
            new TwoLayersFeatureSize(2, 0, 2)
        ).build());
    }

    private static void registerCherryBlossom(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, CHERRY_BLOSSOM_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(Blocks.CHERRY_LOG),
            new CherryTrunkPlacer(7, 3, 2, new java.util.HashMap<>(), ConstantInt.of(2)),
            BlockStateProvider.simple(ModBlocks.CHERRY_BLOSSOM_LEAVES.get()),
            new CherryFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0), ConstantInt.of(5), 0.25f, 0.5f, 0.16666667f, 0.33333334f),
            new TwoLayersFeatureSize(2, 0, 2)
        ).build());
    }

    private static void registerTreeOfLife(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(context, TREE_OF_LIFE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.ELDERWOOD_LOG.get()),
            new GiantTrunkPlacer(25, 10, 15),
            BlockStateProvider.simple(ModBlocks.MOONSTONE_BLOCK.get()),
            new BlobFoliagePlacer(ConstantInt.of(8), ConstantInt.of(4), 10),
            new TwoLayersFeatureSize(4, 0, 4)
        ).build());
    }
}
