package com.verdantrealms.item;

import com.verdantrealms.VerdantRealms;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModItemsTest {

    private static final List<String> EXPECTED_FOOD_ITEMS = List.of(
        "WILD_BERRIES", "GLOW_FRUIT", "STAR_ROOT", "MOON_BREAD", "LAVENDER_TEA"
    );

    private static final List<String> EXPECTED_MISC_ITEMS = List.of(
        "CRYSTAL_SHARD", "ANCIENT_SEED"
    );

    private static final List<String> EXPECTED_BLOCK_ITEMS = List.of(
        "AMETHYST_CRYSTAL_BLOCK_ITEM", "AMBER_CRYSTAL_BLOCK_ITEM", "MOONSTONE_BLOCK_ITEM",
        "ELDERWOOD_LOG_ITEM", "ELDERWOOD_PLANKS_ITEM", "ELDERWOOD_LEAVES_ITEM",
        "STARWOOD_LOG_ITEM", "STARWOOD_LEAVES_ITEM",
        "ASHENWOOD_LOG_ITEM", "ASHENWOOD_LEAVES_ITEM",
        "LUMINOUS_MOSS_ITEM", "CHERRY_BLOSSOM_LEAVES_ITEM",
        "LAVENDER_BUSH_ITEM", "GIANT_LILY_PAD_ITEM",
        "FIRE_BLOSSOM_ITEM", "FROST_FLOWER_ITEM", "VOID_ROOTS_ITEM",
        "VOLCANIC_STONE_ITEM", "GLACIAL_STONE_ITEM",
        "FLOATING_GRASS_ITEM", "CORRUPTED_EARTH_ITEM"
    );

    @Test
    void itemsRegistryFieldExists() throws NoSuchFieldException {
        Field field = ModItems.class.getDeclaredField("ITEMS");
        assertNotNull(field);
        assertEquals(DeferredRegister.Items.class, field.getType());
    }

    @Test
    void totalItemCountIsCorrect() {
        long itemCount = Arrays.stream(ModItems.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> DeferredItem.class.isAssignableFrom(f.getType()))
            .count();
        // 5 food + 2 misc + 21 block items = 28
        assertEquals(28, itemCount, "Should have exactly 28 item registrations");
    }

    @Test
    void allFoodItemsExist() {
        List<String> actualFields = getDeferredItemFieldNames();
        for (String expected : EXPECTED_FOOD_ITEMS) {
            assertTrue(actualFields.contains(expected), "Missing food item: " + expected);
        }
    }

    @Test
    void allMiscItemsExist() {
        List<String> actualFields = getDeferredItemFieldNames();
        for (String expected : EXPECTED_MISC_ITEMS) {
            assertTrue(actualFields.contains(expected), "Missing misc item: " + expected);
        }
    }

    @Test
    void allBlockItemsExist() {
        List<String> actualFields = getDeferredItemFieldNames();
        for (String expected : EXPECTED_BLOCK_ITEMS) {
            assertTrue(actualFields.contains(expected), "Missing block item: " + expected);
        }
    }

    @Test
    void blockItemsEndWithItemSuffix() {
        List<String> blockItems = getDeferredItemFieldNames().stream()
            .filter(name -> name.endsWith("_ITEM"))
            .toList();
        assertEquals(21, blockItems.size(), "Should have 21 block items ending with _ITEM");
    }

    @Test
    void foodItemCountIsCorrect() {
        long foodCount = getDeferredItemFieldNames().stream()
            .filter(name -> EXPECTED_FOOD_ITEMS.contains(name))
            .count();
        assertEquals(5, foodCount, "Should have exactly 5 food items");
    }

    @Test
    void itemFieldsArePublicStaticFinal() {
        for (Field field : ModItems.class.getDeclaredFields()) {
            if (DeferredItem.class.isAssignableFrom(field.getType())) {
                int mods = field.getModifiers();
                assertTrue(Modifier.isPublic(mods), field.getName() + " should be public");
                assertTrue(Modifier.isStatic(mods), field.getName() + " should be static");
                assertTrue(Modifier.isFinal(mods), field.getName() + " should be final");
            }
        }
    }

    @Test
    void itemFieldNamesFollowUpperSnakeCaseConvention() {
        getDeferredItemFieldNames().forEach(name ->
            assertTrue(name.matches("[A-Z][A-Z0-9_]*"),
                "Item field name should be UPPER_SNAKE_CASE: " + name));
    }

    @Test
    void noDuplicateItemFieldNames() {
        List<String> names = getDeferredItemFieldNames();
        assertEquals(names.size(), names.stream().distinct().count(),
            "Item field names should be unique");
    }

    @Test
    void itemsRegistryFieldIsPublicStaticFinal() throws NoSuchFieldException {
        Field field = ModItems.class.getDeclaredField("ITEMS");
        int mods = field.getModifiers();
        assertTrue(Modifier.isPublic(mods));
        assertTrue(Modifier.isStatic(mods));
        assertTrue(Modifier.isFinal(mods));
    }

    private List<String> getDeferredItemFieldNames() {
        return Arrays.stream(ModItems.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> DeferredItem.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .toList();
    }
}
