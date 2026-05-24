package com.verdantrealms.biome;

import com.verdantrealms.VerdantRealms;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {
    // 12 Custom Biomes
    public static final ResourceKey<Biome> CRYSTAL_CAVERNS = register("crystal_caverns");
    public static final ResourceKey<Biome> ETHEREAL_GROVE = register("ethereal_grove");
    public static final ResourceKey<Biome> EMBER_WASTES = register("ember_wastes");
    public static final ResourceKey<Biome> FROSTFELL_PEAKS = register("frostfell_peaks");
    public static final ResourceKey<Biome> SKYWARD_ISLES = register("skyward_isles");
    public static final ResourceKey<Biome> VERDANT_JUNGLE = register("verdant_jungle");
    public static final ResourceKey<Biome> BLOSSOM_VALLEY = register("blossom_valley");
    public static final ResourceKey<Biome> SHADOWMIRE = register("shadowmire");
    public static final ResourceKey<Biome> STARFALL_PLAINS = register("starfall_plains");
    public static final ResourceKey<Biome> VOLCANIC_BADLANDS = register("volcanic_badlands");
    public static final ResourceKey<Biome> ANCIENT_GROVE = register("ancient_grove");
    public static final ResourceKey<Biome> CORRUPTED_REACHES = register("corrupted_reaches");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, name));
    }

    public static void registerBiomes() {
        VerdantRealms.LOGGER.info("Registering VerdantRealms biomes...");
    }

    // Biome Builders
    public static Biome crystalCaverns() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(0.5f)
            .downfall(0.0f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x9966FF)
                .waterFogColor(0x7744CC)
                .fogColor(0x110033)
                .skyColor(0x110033)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.WITCH, 0.02f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome etherealGrove() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addDefaultSoftDisks(gen);
        BiomeDefaultFeatures.addDefaultMushrooms(gen);
        BiomeDefaultFeatures.addDefaultExtraVegetation(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);
        BiomeDefaultFeatures.farmAnimals(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.8f)
            .downfall(0.4f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x44FFAA)
                .waterFogColor(0x22CC88)
                .fogColor(0xCCFFEE)
                .skyColor(0x88FFCC)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER, 0.01f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome emberWastes() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.MONSTER, 
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.BLAZE, 50, 2, 4));

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(2.0f)
            .downfall(0.0f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0xFF4400)
                .waterFogColor(0xCC3300)
                .fogColor(0x331100)
                .skyColor(0xFF6633)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.FLAME, 0.05f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome frostfellPeaks() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addDefaultSoftDisks(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.CREATURE,
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.POLAR_BEAR, 5, 1, 2));

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(-0.8f)
            .downfall(0.9f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0xAADDFF)
                .waterFogColor(0x88BBDD)
                .fogColor(0xDDEEFF)
                .skyColor(0xAACCFF)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.SNOWFLAKE, 0.03f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome skywardIsles() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(0.6f)
            .downfall(0.0f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x66FFFF)
                .waterFogColor(0x44CCCC)
                .fogColor(0x88CCFF)
                .skyColor(0x4488FF)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.END_ROD, 0.02f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome verdantJungle() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addJungleTrees(gen);
        BiomeDefaultFeatures.addDefaultExtraVegetation(gen);
        BiomeDefaultFeatures.addJungleExtraVegetation(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);
        BiomeDefaultFeatures.farmAnimals(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.95f)
            .downfall(0.8f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x44FF88)
                .waterFogColor(0x22CC66)
                .fogColor(0x113322)
                .skyColor(0x44AA66)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER, 0.015f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome blossomValley() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addDefaultSoftDisks(gen);
        BiomeDefaultFeatures.addDefaultMushrooms(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);
        BiomeDefaultFeatures.farmAnimals(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.7f)
            .downfall(0.5f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0xFF88CC)
                .waterFogColor(0xDD66AA)
                .fogColor(0xFFEEF5)
                .skyColor(0xFFCCDD)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.CHERRY_LEAVES, 0.03f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome shadowmire() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.MONSTER,
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.WITCH, 30, 1, 2));
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.MONSTER,
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.SLIME, 50, 2, 4));

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.5f)
            .downfall(0.9f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x224422)
                .waterFogColor(0x113311)
                .fogColor(0x051105)
                .skyColor(0x112211)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.MYCELIUM, 0.04f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome starfallPlains() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addDefaultSoftDisks(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);
        BiomeDefaultFeatures.farmAnimals(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(0.4f)
            .downfall(0.0f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x4444FF)
                .waterFogColor(0x3333CC)
                .fogColor(0x000022)
                .skyColor(0x000044)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.FALLING_DUST, 0.01f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome volcanicBadlands() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.MONSTER,
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.MAGMA_CUBE, 40, 2, 4));

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(1.5f)
            .downfall(0.0f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0xFF2200)
                .waterFogColor(0xCC1100)
                .fogColor(0x331100)
                .skyColor(0xFF4422)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.LAVA, 0.03f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome ancientGrove() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);
        BiomeDefaultFeatures.addDefaultSoftDisks(gen);
        BiomeDefaultFeatures.addDefaultMushrooms(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(spawns);

        return new Biome.BiomeBuilder()
            .hasPrecipitation(true)
            .temperature(0.6f)
            .downfall(0.6f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x44AA88)
                .waterFogColor(0x338866)
                .fogColor(0x224433)
                .skyColor(0x336655)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.SPORE_BLOSSOM, 0.02f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }

    public static Biome corruptedReaches() {
        BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(
            net.minecraft.world.level.levelgen.placement.PlacementUtils.EMPTY,
            net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarvers.CAVE
        );
        BiomeDefaultFeatures.addDefaultOres(gen);

        MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.MONSTER,
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.ENDERMAN, 30, 1, 3));
        spawns.addSpawn(net.minecraft.world.entity.MobCategory.MONSTER,
            new MobSpawnSettings.SpawnerData(net.minecraft.world.entity.EntityType.PHANTOM, 20, 1, 2));

        return new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .temperature(0.8f)
            .downfall(0.0f)
            .specialEffects(new BiomeSpecialEffects.Builder()
                .waterColor(0x440044)
                .waterFogColor(0x220022)
                .fogColor(0x110011)
                .skyColor(0x220033)
                .ambientParticle(new AmbientParticleSettings(net.minecraft.core.particles.ParticleTypes.DRAGON_BREATH, 0.03f))
                .build())
            .mobSpawnSettings(spawns.build())
            .generationSettings(gen.build())
            .build();
    }
}
