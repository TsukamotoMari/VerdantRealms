package com.verdantrealms.util;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public final class BiomeFactory {

    private BiomeFactory() {}

    public enum GenerationFeature {
        DEFAULT_ORES,
        DEFAULT_SOFT_DISKS,
        DEFAULT_MUSHROOMS,
        DEFAULT_EXTRA_VEGETATION,
        JUNGLE_TREES,
        JUNGLE_MELONS,
        JUNGLE_VINES
    }

    public static Builder builder(HolderGetter<PlacedFeature> placedFeatures,
                                  HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        return new Builder(placedFeatures, worldCarvers);
    }

    public static final class Builder {
        private final HolderGetter<PlacedFeature> placedFeatures;
        private final HolderGetter<ConfiguredWorldCarver<?>> worldCarvers;
        private boolean precipitation;
        private float temperature;
        private float downfall;
        private int waterColor;
        private int waterFogColor;
        private int fogColor;
        private int skyColor;
        private ParticleOptions particle;
        private float particleProbability;
        private final EnumSet<GenerationFeature> features = EnumSet.noneOf(GenerationFeature.class);
        private boolean commonSpawns;
        private boolean farmAnimals;
        private final List<SpawnEntry> customSpawns = new ArrayList<>();

        Builder(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
            this.placedFeatures = placedFeatures;
            this.worldCarvers = worldCarvers;
        }

        public Builder precipitation(boolean precipitation) {
            this.precipitation = precipitation;
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        public Builder colors(int waterColor, int waterFogColor, int fogColor, int skyColor) {
            this.waterColor = waterColor;
            this.waterFogColor = waterFogColor;
            this.fogColor = fogColor;
            this.skyColor = skyColor;
            return this;
        }

        public Builder ambientParticle(ParticleOptions particle, float probability) {
            this.particle = particle;
            this.particleProbability = probability;
            return this;
        }

        public Builder features(GenerationFeature... features) {
            for (GenerationFeature f : features) {
                this.features.add(f);
            }
            return this;
        }

        public Builder commonSpawns() {
            this.commonSpawns = true;
            return this;
        }

        public Builder farmAnimals() {
            this.farmAnimals = true;
            return this;
        }

        public Builder spawn(MobCategory category, EntityType<?> type, int weight, int min, int max) {
            customSpawns.add(new SpawnEntry(category, type, weight, min, max));
            return this;
        }

        public Biome build() {
            BiomeGenerationSettings.Builder gen = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
            applyFeatures(gen);

            MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
            applySpawns(spawns);

            return new Biome.BiomeBuilder()
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(new BiomeSpecialEffects.Builder()
                    .waterColor(waterColor)
                    .waterFogColor(waterFogColor)
                    .fogColor(fogColor)
                    .skyColor(skyColor)
                    .ambientParticle(new AmbientParticleSettings(particle, particleProbability))
                    .build())
                .mobSpawnSettings(spawns.build())
                .generationSettings(gen.build())
                .build();
        }

        private void applyFeatures(BiomeGenerationSettings.Builder gen) {
            if (features.contains(GenerationFeature.DEFAULT_ORES)) BiomeDefaultFeatures.addDefaultOres(gen);
            if (features.contains(GenerationFeature.DEFAULT_SOFT_DISKS)) BiomeDefaultFeatures.addDefaultSoftDisks(gen);
            if (features.contains(GenerationFeature.DEFAULT_MUSHROOMS)) BiomeDefaultFeatures.addDefaultMushrooms(gen);
            if (features.contains(GenerationFeature.DEFAULT_EXTRA_VEGETATION)) BiomeDefaultFeatures.addDefaultExtraVegetation(gen);
            if (features.contains(GenerationFeature.JUNGLE_TREES)) BiomeDefaultFeatures.addJungleTrees(gen);
            if (features.contains(GenerationFeature.JUNGLE_MELONS)) BiomeDefaultFeatures.addJungleMelons(gen);
            if (features.contains(GenerationFeature.JUNGLE_VINES)) BiomeDefaultFeatures.addJungleVines(gen);
        }

        private void applySpawns(MobSpawnSettings.Builder spawns) {
            if (commonSpawns) BiomeDefaultFeatures.commonSpawns(spawns);
            if (farmAnimals) BiomeDefaultFeatures.farmAnimals(spawns);
            for (SpawnEntry entry : customSpawns) {
                spawns.addSpawn(entry.category,
                    new MobSpawnSettings.SpawnerData(entry.type, entry.weight, entry.min, entry.max));
            }
        }
    }

    private record SpawnEntry(MobCategory category, EntityType<?> type, int weight, int min, int max) {}
}
