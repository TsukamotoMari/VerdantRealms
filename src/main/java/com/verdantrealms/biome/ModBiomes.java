package com.verdantrealms.biome;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.util.BiomeFactory;
import com.verdantrealms.util.BiomeFactory.GenerationFeature;
import com.verdantrealms.util.RegistryHelper;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModBiomes {
    // 12 Custom Biomes
    public static final ResourceKey<Biome> CRYSTAL_CAVERNS = RegistryHelper.key(Registries.BIOME, "crystal_caverns");
    public static final ResourceKey<Biome> ETHEREAL_GROVE = RegistryHelper.key(Registries.BIOME, "ethereal_grove");
    public static final ResourceKey<Biome> EMBER_WASTES = RegistryHelper.key(Registries.BIOME, "ember_wastes");
    public static final ResourceKey<Biome> FROSTFELL_PEAKS = RegistryHelper.key(Registries.BIOME, "frostfell_peaks");
    public static final ResourceKey<Biome> SKYWARD_ISLES = RegistryHelper.key(Registries.BIOME, "skyward_isles");
    public static final ResourceKey<Biome> VERDANT_JUNGLE = RegistryHelper.key(Registries.BIOME, "verdant_jungle");
    public static final ResourceKey<Biome> BLOSSOM_VALLEY = RegistryHelper.key(Registries.BIOME, "blossom_valley");
    public static final ResourceKey<Biome> SHADOWMIRE = RegistryHelper.key(Registries.BIOME, "shadowmire");
    public static final ResourceKey<Biome> STARFALL_PLAINS = RegistryHelper.key(Registries.BIOME, "starfall_plains");
    public static final ResourceKey<Biome> VOLCANIC_BADLANDS = RegistryHelper.key(Registries.BIOME, "volcanic_badlands");
    public static final ResourceKey<Biome> ANCIENT_GROVE = RegistryHelper.key(Registries.BIOME, "ancient_grove");
    public static final ResourceKey<Biome> CORRUPTED_REACHES = RegistryHelper.key(Registries.BIOME, "corrupted_reaches");

    public static void registerBiomes() {
        VerdantRealms.LOGGER.info("Registering VerdantRealms biomes...");
    }

    private static BiomeFactory.Builder base(HolderGetter<PlacedFeature> placed,
                                             HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return BiomeFactory.builder(placed, carvers);
    }

    // Biome Builders
    public static Biome crystalCaverns(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(false).temperature(0.5f).downfall(0.0f)
            .colors(0x9966FF, 0x7744CC, 0x110033, 0x110033)
            .ambientParticle(ParticleTypes.WITCH, 0.02f)
            .commonSpawns()
            .build();
    }

    public static Biome etherealGrove(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(true).temperature(0.8f).downfall(0.4f)
            .colors(0x44FFAA, 0x22CC88, 0xCCFFEE, 0x88FFCC)
            .ambientParticle(ParticleTypes.HAPPY_VILLAGER, 0.01f)
            .features(GenerationFeature.DEFAULT_ORES, GenerationFeature.DEFAULT_SOFT_DISKS,
                      GenerationFeature.DEFAULT_MUSHROOMS, GenerationFeature.DEFAULT_EXTRA_VEGETATION)
            .commonSpawns().farmAnimals()
            .build();
    }

    public static Biome emberWastes(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(false).temperature(2.0f).downfall(0.0f)
            .colors(0xFF4400, 0xCC3300, 0x331100, 0xFF6633)
            .ambientParticle(ParticleTypes.FLAME, 0.05f)
            .features(GenerationFeature.DEFAULT_ORES)
            .spawn(MobCategory.MONSTER, EntityType.BLAZE, 50, 2, 4)
            .build();
    }

    public static Biome frostfellPeaks(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(true).temperature(-0.8f).downfall(0.9f)
            .colors(0xAADDFF, 0x88BBDD, 0xDDEEFF, 0xAACCFF)
            .ambientParticle(ParticleTypes.SNOWFLAKE, 0.03f)
            .features(GenerationFeature.DEFAULT_ORES, GenerationFeature.DEFAULT_SOFT_DISKS)
            .commonSpawns()
            .spawn(MobCategory.CREATURE, EntityType.POLAR_BEAR, 5, 1, 2)
            .build();
    }

    public static Biome skywardIsles(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(false).temperature(0.6f).downfall(0.0f)
            .colors(0x66FFFF, 0x44CCCC, 0x88CCFF, 0x4488FF)
            .ambientParticle(ParticleTypes.END_ROD, 0.02f)
            .commonSpawns()
            .build();
    }

    public static Biome verdantJungle(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(true).temperature(0.95f).downfall(0.8f)
            .colors(0x44FF88, 0x22CC66, 0x113322, 0x44AA66)
            .ambientParticle(ParticleTypes.HAPPY_VILLAGER, 0.015f)
            .features(GenerationFeature.DEFAULT_ORES, GenerationFeature.DEFAULT_EXTRA_VEGETATION,
                      GenerationFeature.JUNGLE_TREES, GenerationFeature.JUNGLE_MELONS,
                      GenerationFeature.JUNGLE_VINES)
            .commonSpawns().farmAnimals()
            .build();
    }

    public static Biome blossomValley(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(true).temperature(0.7f).downfall(0.5f)
            .colors(0xFF88CC, 0xDD66AA, 0xFFEEF5, 0xFFCCDD)
            .ambientParticle(ParticleTypes.CHERRY_LEAVES, 0.03f)
            .features(GenerationFeature.DEFAULT_ORES, GenerationFeature.DEFAULT_SOFT_DISKS,
                      GenerationFeature.DEFAULT_MUSHROOMS)
            .commonSpawns().farmAnimals()
            .build();
    }

    public static Biome shadowmire(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(true).temperature(0.5f).downfall(0.9f)
            .colors(0x224422, 0x113311, 0x051105, 0x112211)
            .ambientParticle(ParticleTypes.MYCELIUM, 0.04f)
            .features(GenerationFeature.DEFAULT_ORES)
            .spawn(MobCategory.MONSTER, EntityType.WITCH, 30, 1, 2)
            .spawn(MobCategory.MONSTER, EntityType.SLIME, 50, 2, 4)
            .build();
    }

    public static Biome starfallPlains(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(false).temperature(0.4f).downfall(0.0f)
            .colors(0x4444FF, 0x3333CC, 0x000022, 0x000044)
            .ambientParticle(ParticleTypes.END_ROD, 0.01f)
            .features(GenerationFeature.DEFAULT_ORES, GenerationFeature.DEFAULT_SOFT_DISKS)
            .commonSpawns().farmAnimals()
            .build();
    }

    public static Biome volcanicBadlands(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(false).temperature(1.5f).downfall(0.0f)
            .colors(0xFF2200, 0xCC1100, 0x331100, 0xFF4422)
            .ambientParticle(ParticleTypes.LAVA, 0.03f)
            .features(GenerationFeature.DEFAULT_ORES)
            .spawn(MobCategory.MONSTER, EntityType.MAGMA_CUBE, 40, 2, 4)
            .build();
    }

    public static Biome ancientGrove(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(true).temperature(0.6f).downfall(0.6f)
            .colors(0x44AA88, 0x338866, 0x224433, 0x336655)
            .ambientParticle(ParticleTypes.SPORE_BLOSSOM_AIR, 0.02f)
            .features(GenerationFeature.DEFAULT_ORES, GenerationFeature.DEFAULT_SOFT_DISKS,
                      GenerationFeature.DEFAULT_MUSHROOMS)
            .commonSpawns()
            .build();
    }

    public static Biome corruptedReaches(HolderGetter<PlacedFeature> placed, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return base(placed, carvers)
            .precipitation(false).temperature(0.8f).downfall(0.0f)
            .colors(0x440044, 0x220022, 0x110011, 0x220033)
            .ambientParticle(ParticleTypes.DRAGON_BREATH, 0.03f)
            .features(GenerationFeature.DEFAULT_ORES)
            .spawn(MobCategory.MONSTER, EntityType.ENDERMAN, 30, 1, 3)
            .spawn(MobCategory.MONSTER, EntityType.PHANTOM, 20, 1, 2)
            .build();
    }
}
