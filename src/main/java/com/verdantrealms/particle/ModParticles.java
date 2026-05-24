package com.verdantrealms.particle;

import com.verdantrealms.VerdantRealms;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, VerdantRealms.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLOW_SPORE = PARTICLE_TYPES.register("glow_spore", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_STAR = PARTICLE_TYPES.register("falling_star", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EMBER = PARTICLE_TYPES.register("ember", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRYSTAL_DUST = PARTICLE_TYPES.register("crystal_dust", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FROST_MOTE = PARTICLE_TYPES.register("frost_mote", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SHADOW_TENDRIL = PARTICLE_TYPES.register("shadow_tendril", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOSSOM_PETAL = PARTICLE_TYPES.register("blossom_petal", () -> new SimpleParticleType(true));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOLCANIC_ASH = PARTICLE_TYPES.register("volcanic_ash", () -> new SimpleParticleType(true));
}
