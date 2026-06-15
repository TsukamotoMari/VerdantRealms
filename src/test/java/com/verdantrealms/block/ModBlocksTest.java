package com.verdantrealms.block;

import com.verdantrealms.VerdantRealms;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModBlocksTest {

    private static final List<String> EXPECTED_BLOCK_NAMES = List.of(
        // Crystal blocks
        "AMETHYST_CRYSTAL_BLOCK", "AMBER_CRYSTAL_BLOCK", "MOONSTONE_BLOCK",
        // Wood types
        "ELDERWOOD_LOG", "ELDERWOOD_PLANKS", "ELDERWOOD_LEAVES",
        "STARWOOD_LOG", "STARWOOD_LEAVES",
        "ASHENWOOD_LOG", "ASHENWOOD_LEAVES",
        // Foliage & Plants
        "LUMINOUS_MOSS", "GLOWING_VINES", "CHERRY_BLOSSOM_LEAVES",
        "LAVENDER_BUSH", "GIANT_LILY_PAD", "FIRE_BLOSSOM", "FROST_FLOWER", "VOID_ROOTS",
        // Edible plants
        "WILD_BERRY_BUSH", "GLOW_FRUIT_BUSH", "STAR_ROOT_CROP", "MOON_WHEAT_CROP",
        // Terrain
        "VOLCANIC_STONE", "GLACIAL_STONE", "FLOATING_GRASS", "CORRUPTED_EARTH"
    );

    @Test
    void blocksRegistryFieldExists() throws NoSuchFieldException {
        Field field = ModBlocks.class.getDeclaredField("BLOCKS");
        assertNotNull(field);
        assertEquals(DeferredRegister.Blocks.class, field.getType());
    }

    @Test
    void correctNumberOfBlocksRegistered() {
        long blockCount = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .count();
        assertEquals(26, blockCount, "Should have exactly 26 block registrations");
    }

    @Test
    void allExpectedBlocksExist() {
        List<String> actualFields = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .toList();

        for (String expected : EXPECTED_BLOCK_NAMES) {
            assertTrue(actualFields.contains(expected),
                "Missing block: " + expected);
        }
    }

    @Test
    void blockFieldsArePublicStaticFinal() {
        for (Field field : ModBlocks.class.getDeclaredFields()) {
            if (DeferredBlock.class.isAssignableFrom(field.getType())) {
                int mods = field.getModifiers();
                assertTrue(Modifier.isPublic(mods), field.getName() + " should be public");
                assertTrue(Modifier.isStatic(mods), field.getName() + " should be static");
                assertTrue(Modifier.isFinal(mods), field.getName() + " should be final");
            }
        }
    }

    @Test
    void blockFieldNamesFollowUpperSnakeCaseConvention() {
        Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .forEach(f -> assertTrue(f.getName().matches("[A-Z][A-Z0-9_]*"),
                "Block field name should be UPPER_SNAKE_CASE: " + f.getName()));
    }

    @Test
    void crystalBlocksExist() {
        List<String> crystalBlocks = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .filter(name -> name.contains("CRYSTAL") || name.contains("MOONSTONE"))
            .toList();
        assertEquals(3, crystalBlocks.size(), "Should have 3 crystal-type blocks");
    }

    @Test
    void woodTypesExist() {
        List<String> logBlocks = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .filter(name -> name.contains("LOG"))
            .toList();
        assertEquals(3, logBlocks.size(), "Should have 3 log blocks (elderwood, starwood, ashenwood)");
    }

    @Test
    void leaveBlocksExist() {
        List<String> leavesBlocks = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .filter(name -> name.contains("LEAVES"))
            .toList();
        assertEquals(4, leavesBlocks.size(),
            "Should have 4 leaves blocks (elderwood, starwood, ashenwood, cherry_blossom)");
    }

    @Test
    void cropBlocksExist() {
        List<String> cropBlocks = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .filter(name -> name.contains("CROP"))
            .toList();
        assertEquals(2, cropBlocks.size(), "Should have 2 crop blocks");
    }

    @Test
    void terrainBlocksExist() {
        List<String> terrainNames = List.of("VOLCANIC_STONE", "GLACIAL_STONE", "FLOATING_GRASS", "CORRUPTED_EARTH");
        List<String> actualFields = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .toList();

        for (String expected : terrainNames) {
            assertTrue(actualFields.contains(expected), "Missing terrain block: " + expected);
        }
    }

    @Test
    void noDuplicateBlockFieldNames() {
        List<String> names = Arrays.stream(ModBlocks.class.getDeclaredFields())
            .filter(f -> DeferredBlock.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .toList();
        assertEquals(names.size(), names.stream().distinct().count(),
            "Block field names should be unique");
    }

    @Test
    void blocksRegistryFieldIsPublicStaticFinal() throws NoSuchFieldException {
        Field field = ModBlocks.class.getDeclaredField("BLOCKS");
        int mods = field.getModifiers();
        assertTrue(Modifier.isPublic(mods));
        assertTrue(Modifier.isStatic(mods));
        assertTrue(Modifier.isFinal(mods));
    }
}
