package com.verdantrealms.worldgen.feature;

import com.verdantrealms.VerdantRealms;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModConfiguredFeaturesTest {

    private static final List<String> EXPECTED_FEATURE_PATHS = List.of(
        "elderwood", "starwood", "ashenwood", "cherry_blossom", "tree_of_life",
        "giant_mushroom", "crystal_spike", "luminous_vines",
        "floating_island", "volcanic_vent", "frost_spire", "ancient_ruins"
    );

    @Test
    void correctNumberOfFeaturesRegistered() {
        long featureCount = Arrays.stream(ModConfiguredFeatures.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(ResourceKey.class))
            .count();
        assertEquals(12, featureCount, "Should have exactly 12 configured feature keys");
    }

    @SuppressWarnings("unchecked")
    @Test
    void featureKeysUseCorrectNamespace() throws IllegalAccessException {
        for (Field field : ModConfiguredFeatures.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<ConfiguredFeature<?, ?>> key =
                    (ResourceKey<ConfiguredFeature<?, ?>>) field.get(null);
                assertEquals(VerdantRealms.MOD_ID, key.location().getNamespace(),
                    "Feature key " + field.getName() + " should use mod namespace");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void allExpectedFeaturesExist() throws IllegalAccessException {
        List<String> actualPaths = Arrays.stream(ModConfiguredFeatures.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(ResourceKey.class))
            .map(f -> {
                try {
                    return ((ResourceKey<?>) f.get(null)).location().getPath();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        for (String expected : EXPECTED_FEATURE_PATHS) {
            assertTrue(actualPaths.contains(expected),
                "Missing feature: " + expected);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void featureKeyNamesAreLowerSnakeCase() throws IllegalAccessException {
        for (Field field : ModConfiguredFeatures.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<?> key = (ResourceKey<?>) field.get(null);
                String path = key.location().getPath();
                assertTrue(path.matches("[a-z][a-z0-9_]*"),
                    "Feature path should be lower_snake_case: " + path);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void featureKeysAreInConfiguredFeatureRegistry() throws IllegalAccessException {
        for (Field field : ModConfiguredFeatures.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.getType().equals(ResourceKey.class)) {
                ResourceKey<?> key = (ResourceKey<?>) field.get(null);
                assertEquals("minecraft:worldgen/configured_feature",
                    key.registry().toString(),
                    "Feature " + field.getName() + " should be in configured_feature registry");
            }
        }
    }

    @Test
    void featureFieldsArePublicStaticFinal() {
        for (Field field : ModConfiguredFeatures.class.getDeclaredFields()) {
            if (field.getType().equals(ResourceKey.class)) {
                int mods = field.getModifiers();
                assertTrue(Modifier.isPublic(mods), field.getName() + " should be public");
                assertTrue(Modifier.isStatic(mods), field.getName() + " should be static");
                assertTrue(Modifier.isFinal(mods), field.getName() + " should be final");
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    void noDuplicateFeatureKeys() throws IllegalAccessException {
        List<String> paths = Arrays.stream(ModConfiguredFeatures.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> f.getType().equals(ResourceKey.class))
            .map(f -> {
                try {
                    return ((ResourceKey<?>) f.get(null)).location().getPath();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();

        assertEquals(paths.size(), paths.stream().distinct().count(),
            "Feature keys should have no duplicates");
    }

    @Test
    void treeFeatureKeysExist() {
        List<String> treeNames = List.of("ELDERWOOD_TREE", "STARWOOD_TREE", "ASHENWOOD_TREE",
            "CHERRY_BLOSSOM_TREE", "TREE_OF_LIFE");

        List<String> actualFields = Arrays.stream(ModConfiguredFeatures.class.getDeclaredFields())
            .filter(f -> f.getType().equals(ResourceKey.class))
            .map(Field::getName)
            .toList();

        for (String expected : treeNames) {
            assertTrue(actualFields.contains(expected),
                "Missing tree feature: " + expected);
        }
    }

    @Test
    void registerMethodExists() throws NoSuchMethodException {
        Method method = ModConfiguredFeatures.class.getMethod("register");
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void registerKeyMethodExists() throws NoSuchMethodException {
        Method method = ModConfiguredFeatures.class.getMethod("registerKey", String.class);
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(ResourceKey.class, method.getReturnType());
    }

    @Test
    void registerKeyProducesValidKey() {
        ResourceKey<ConfiguredFeature<?, ?>> key = ModConfiguredFeatures.registerKey("test_feature");
        assertNotNull(key);
        assertEquals(VerdantRealms.MOD_ID, key.location().getNamespace());
        assertEquals("test_feature", key.location().getPath());
    }
}
