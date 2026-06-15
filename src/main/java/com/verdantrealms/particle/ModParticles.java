package com.verdantrealms.particle;

import com.verdantrealms.VerdantRealms;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, VerdantRealms.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLOW_SPORE = registerParticle("glow_spore");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_STAR = registerParticle("falling_star");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EMBER = registerParticle("ember");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRYSTAL_DUST = registerParticle("crystal_dust");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FROST_MOTE = registerParticle("frost_mote");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SHADOW_TENDRIL = registerParticle("shadow_tendril");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> BLOSSOM_PETAL = registerParticle("blossom_petal");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOLCANIC_ASH = registerParticle("volcanic_ash");

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> registerParticle(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(true));
    }
}
