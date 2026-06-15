package com.verdantrealms.particle;

import com.verdantrealms.VerdantRealms;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModParticlesTest {

    private static final List<String> EXPECTED_PARTICLE_NAMES = List.of(
        "GLOW_SPORE", "FALLING_STAR", "EMBER", "CRYSTAL_DUST",
        "FROST_MOTE", "SHADOW_TENDRIL", "BLOSSOM_PETAL", "VOLCANIC_ASH"
    );

    @Test
    void particleTypesRegistryFieldExists() throws NoSuchFieldException {
        Field field = ModParticles.class.getDeclaredField("PARTICLE_TYPES");
        assertNotNull(field);
        assertEquals(DeferredRegister.class, field.getType());
    }

    @Test
    void correctNumberOfParticlesRegistered() {
        long particleCount = Arrays.stream(ModParticles.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> DeferredHolder.class.isAssignableFrom(f.getType()))
            .count();
        assertEquals(8, particleCount, "Should have exactly 8 particle type registrations");
    }

    @Test
    void allExpectedParticlesExist() {
        List<String> actualFields = getParticleFieldNames();
        for (String expected : EXPECTED_PARTICLE_NAMES) {
            assertTrue(actualFields.contains(expected),
                "Missing particle: " + expected);
        }
    }

    @Test
    void particleFieldsArePublicStaticFinal() {
        for (Field field : ModParticles.class.getDeclaredFields()) {
            if (DeferredHolder.class.isAssignableFrom(field.getType())) {
                int mods = field.getModifiers();
                assertTrue(Modifier.isPublic(mods), field.getName() + " should be public");
                assertTrue(Modifier.isStatic(mods), field.getName() + " should be static");
                assertTrue(Modifier.isFinal(mods), field.getName() + " should be final");
            }
        }
    }

    @Test
    void particleFieldNamesFollowUpperSnakeCaseConvention() {
        getParticleFieldNames().forEach(name ->
            assertTrue(name.matches("[A-Z][A-Z0-9_]*"),
                "Particle field name should be UPPER_SNAKE_CASE: " + name));
    }

    @Test
    void noDuplicateParticleNames() {
        List<String> names = getParticleFieldNames();
        assertEquals(names.size(), names.stream().distinct().count(),
            "Particle names should be unique");
    }

    @Test
    void particleTypesFieldExists() throws NoSuchFieldException {
        Field field = ModParticles.class.getDeclaredField("PARTICLE_TYPES");
        int mods = field.getModifiers();
        assertTrue(Modifier.isPublic(mods));
        assertTrue(Modifier.isStatic(mods));
        assertTrue(Modifier.isFinal(mods));
    }

    private List<String> getParticleFieldNames() {
        return Arrays.stream(ModParticles.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> DeferredHolder.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .toList();
    }
}
