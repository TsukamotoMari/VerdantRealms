package com.verdantrealms;

import com.verdantrealms.biome.ModBiomes;
import com.verdantrealms.block.ModBlocks;
import com.verdantrealms.item.ModItems;
import com.verdantrealms.particle.ModParticles;
import com.verdantrealms.worldgen.feature.ModConfiguredFeatures;
import com.verdantrealms.worldgen.tree.ModTreeGrowers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(VerdantRealms.MOD_ID)
public class VerdantRealms {
    public static final String MOD_ID = "verdantrealms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public VerdantRealms(IEventBus modEventBus) {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModTreeGrowers.register();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("VerdantRealms world generation initializing...");
        event.enqueueWork(() -> {
            ModBiomes.registerBiomes();
            ModConfiguredFeatures.register();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("VerdantRealms client effects loading...");
    }
}
