package com.verdantrealms.datagen;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.biome.ModBiomes;
import com.verdantrealms.worldgen.feature.ModConfiguredFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
        .add(Registries.BIOME, context -> {
            HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
            HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

            context.register(ModBiomes.CRYSTAL_CAVERNS, ModBiomes.crystalCaverns(placedFeatures, carvers));
            context.register(ModBiomes.ETHEREAL_GROVE, ModBiomes.etherealGrove(placedFeatures, carvers));
            context.register(ModBiomes.EMBER_WASTES, ModBiomes.emberWastes(placedFeatures, carvers));
            context.register(ModBiomes.FROSTFELL_PEAKS, ModBiomes.frostfellPeaks(placedFeatures, carvers));
            context.register(ModBiomes.SKYWARD_ISLES, ModBiomes.skywardIsles(placedFeatures, carvers));
            context.register(ModBiomes.VERDANT_JUNGLE, ModBiomes.verdantJungle(placedFeatures, carvers));
            context.register(ModBiomes.BLOSSOM_VALLEY, ModBiomes.blossomValley(placedFeatures, carvers));
            context.register(ModBiomes.SHADOWMIRE, ModBiomes.shadowmire(placedFeatures, carvers));
            context.register(ModBiomes.STARFALL_PLAINS, ModBiomes.starfallPlains(placedFeatures, carvers));
            context.register(ModBiomes.VOLCANIC_BADLANDS, ModBiomes.volcanicBadlands(placedFeatures, carvers));
            context.register(ModBiomes.ANCIENT_GROVE, ModBiomes.ancientGrove(placedFeatures, carvers));
            context.register(ModBiomes.CORRUPTED_REACHES, ModBiomes.corruptedReaches(placedFeatures, carvers));
        });

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(VerdantRealms.MOD_ID));
    }
}
