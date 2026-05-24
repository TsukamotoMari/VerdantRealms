package com.verdantrealms.datagen;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.biome.ModBiomes;
import com.verdantrealms.worldgen.feature.ModConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
        .add(Registries.BIOME, context -> {
            context.register(ModBiomes.CRYSTAL_CAVERNS, ModBiomes.crystalCaverns());
            context.register(ModBiomes.ETHEREAL_GROVE, ModBiomes.etherealGrove());
            context.register(ModBiomes.EMBER_WASTES, ModBiomes.emberWastes());
            context.register(ModBiomes.FROSTFELL_PEAKS, ModBiomes.frostfellPeaks());
            context.register(ModBiomes.SKYWARD_ISLES, ModBiomes.skywardIsles());
            context.register(ModBiomes.VERDANT_JUNGLE, ModBiomes.verdantJungle());
            context.register(ModBiomes.BLOSSOM_VALLEY, ModBiomes.blossomValley());
            context.register(ModBiomes.SHADOWMIRE, ModBiomes.shadowmire());
            context.register(ModBiomes.STARFALL_PLAINS, ModBiomes.starfallPlains());
            context.register(ModBiomes.VOLCANIC_BADLANDS, ModBiomes.volcanicBadlands());
            context.register(ModBiomes.ANCIENT_GROVE, ModBiomes.ancientGrove());
            context.register(ModBiomes.CORRUPTED_REACHES, ModBiomes.corruptedReaches());
        });

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(VerdantRealms.MOD_ID));
    }
}
