package com.verdantrealms.datagen;

import com.verdantrealms.VerdantRealms;
import com.verdantrealms.biome.ModBiomes;
import com.verdantrealms.worldgen.feature.ModConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
        .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
        .add(Registries.BIOME, ModWorldGenProvider::bootstrapBiomes);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(VerdantRealms.MOD_ID));
    }

    private static void bootstrapBiomes(BootstrapContext<Biome> context) {
        try {
            registerBiome(context, ModBiomes.CRYSTAL_CAVERNS, ModBiomes::crystalCaverns);
            registerBiome(context, ModBiomes.ETHEREAL_GROVE, ModBiomes::etherealGrove);
            registerBiome(context, ModBiomes.EMBER_WASTES, ModBiomes::emberWastes);
            registerBiome(context, ModBiomes.FROSTFELL_PEAKS, ModBiomes::frostfellPeaks);
            registerBiome(context, ModBiomes.SKYWARD_ISLES, ModBiomes::skywardIsles);
            registerBiome(context, ModBiomes.VERDANT_JUNGLE, ModBiomes::verdantJungle);
            registerBiome(context, ModBiomes.BLOSSOM_VALLEY, ModBiomes::blossomValley);
            registerBiome(context, ModBiomes.SHADOWMIRE, ModBiomes::shadowmire);
            registerBiome(context, ModBiomes.STARFALL_PLAINS, ModBiomes::starfallPlains);
            registerBiome(context, ModBiomes.VOLCANIC_BADLANDS, ModBiomes::volcanicBadlands);
            registerBiome(context, ModBiomes.ANCIENT_GROVE, ModBiomes::ancientGrove);
            registerBiome(context, ModBiomes.CORRUPTED_REACHES, ModBiomes::corruptedReaches);
            VerdantRealms.LOGGER.info("All VerdantRealms biomes bootstrapped successfully");
        } catch (Exception e) {
            VerdantRealms.LOGGER.error("Failed to bootstrap VerdantRealms biomes", e);
            throw new RuntimeException("VerdantRealms biome bootstrap failed", e);
        }
    }

    private static void registerBiome(BootstrapContext<Biome> context, ResourceKey<Biome> key, Supplier<Biome> biomeSupplier) {
        try {
            context.register(key, biomeSupplier.get());
        } catch (Exception e) {
            VerdantRealms.LOGGER.error("Failed to register biome: {}", key.location(), e);
            throw new RuntimeException("Failed to register biome: " + key.location(), e);
        }
    }
}
