package com.verdantrealms.biome;

import com.verdantrealms.VerdantRealms;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ModBiomesTest {

    private static final List<String> EXPECTED_BIOME_NAMES = List.of(
        "crystal_caverns", "ethereal_grove", "ember_wastes", "frostfell_peaks",
        "skyward_isles", "verdant_jungle", "blossom_valley", "shadowmire",
        "starfall_plains", "volcanic_badlands", "ancient_grove", "corrupted_reaches"
    );

    @Test
    void allTwelveBiomeKeysAreDefined() {
        long biomeKeyCount = Arrays.stream(ModBiomes.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(ResourceKey.class))
            .count();
        assertEquals(12, biomeKeyCount, "Should have exactly 12 biome ResourceKeys");
    }

    @SuppressWarnings("unchecked")
    @Test
    void biomeKeysUseCorrectNamespace() throws IllegalAccessException {
        for (Field field : ModBiomes.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<Biome> key = (ResourceKey<Biome>) field.get(null);
                assertEquals(VerdantRealms.MOD_ID, key.location().getNamespace(),
                    "Biome key " + field.getName() + " should use mod namespace");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void biomeKeysMatchExpectedNames() throws IllegalAccessException {
        for (Field field : ModBiomes.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<Biome> key = (ResourceKey<Biome>) field.get(null);
                String path = key.location().getPath();
                assertTrue(EXPECTED_BIOME_NAMES.contains(path),
                    "Unexpected biome path: " + path);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void allExpectedBiomesAreRegistered() throws IllegalAccessException {
        List<String> actualPaths = Arrays.stream(ModBiomes.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(ResourceKey.class))
            .map(f -> {
                try {
                    return ((ResourceKey<Biome>) f.get(null)).location().getPath();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        for (String expected : EXPECTED_BIOME_NAMES) {
            assertTrue(actualPaths.contains(expected),
                "Missing biome: " + expected);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void biomeKeyNamesAreLowerSnakeCase() throws IllegalAccessException {
        for (Field field : ModBiomes.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<Biome> key = (ResourceKey<Biome>) field.get(null);
                String path = key.location().getPath();
                assertTrue(path.matches("[a-z][a-z0-9_]*"),
                    "Biome path should be lower_snake_case: " + path);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void biomeKeysAreInBiomeRegistry() throws IllegalAccessException {
        for (Field field : ModBiomes.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<Biome> key = (ResourceKey<Biome>) field.get(null);
                assertEquals("minecraft:worldgen/biome", key.registry().toString(),
                    "Biome " + field.getName() + " should be in biome registry");
            }
        }
    }

    @Test
    void biomeKeysArePublicStaticFinal() {
        for (Field field : ModBiomes.class.getDeclaredFields()) {
            if (field.getType().equals(ResourceKey.class)) {
                int mods = field.getModifiers();
                assertTrue(Modifier.isPublic(mods), field.getName() + " should be public");
                assertTrue(Modifier.isStatic(mods), field.getName() + " should be static");
                assertTrue(Modifier.isFinal(mods), field.getName() + " should be final");
            }
        }
    }

    @Test
    void eachBiomeHasCorrespondingBuilderMethod() {
        List<String> biomeBuilderMethods = Arrays.stream(ModBiomes.class.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers()))
            .filter(m -> m.getReturnType().equals(Biome.class))
            .filter(m -> m.getParameterCount() == 2)
            .map(Method::getName)
            .toList();

        List<String> expectedBuilders = List.of(
            "crystalCaverns", "etherealGrove", "emberWastes", "frostfellPeaks",
            "skywardIsles", "verdantJungle", "blossomValley", "shadowmire",
            "starfallPlains", "volcanicBadlands", "ancientGrove", "corruptedReaches"
        );

        for (String expected : expectedBuilders) {
            assertTrue(biomeBuilderMethods.contains(expected),
                "Missing biome builder method: " + expected);
        }
        assertEquals(12, biomeBuilderMethods.size(),
            "Should have exactly 12 biome builder methods");
    }

    @Test
    void registerBiomesMethodExists() throws NoSuchMethodException {
        Method method = ModBiomes.class.getMethod("registerBiomes");
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(void.class, method.getReturnType());
    }

    @SuppressWarnings("unchecked")
    @Test
    void noDuplicateBiomeKeys() throws IllegalAccessException {
        List<String> paths = Arrays.stream(ModBiomes.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(ResourceKey.class))
            .map(f -> {
                try {
                    return ((ResourceKey<Biome>) f.get(null)).location().getPath();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        assertEquals(paths.size(), paths.stream().distinct().count(),
            "Biome keys should have no duplicates");
    }

    @SuppressWarnings("unchecked")
    @Test
    void fieldNameMatchesBiomePath() throws IllegalAccessException {
        for (Field field : ModBiomes.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<Biome> key = (ResourceKey<Biome>) field.get(null);
                String expectedPath = field.getName().toLowerCase();
                assertEquals(expectedPath, key.location().getPath(),
                    "Field " + field.getName() + " should match its ResourceKey path");
            }
        }
    }
}
