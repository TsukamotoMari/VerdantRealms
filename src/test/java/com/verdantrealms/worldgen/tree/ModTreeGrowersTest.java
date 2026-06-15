package com.verdantrealms.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModTreeGrowersTest {

    private static final List<String> EXPECTED_GROWERS = List.of(
        "ELDERWOOD", "STARWOOD", "ASHENWOOD", "CHERRY_BLOSSOM", "TREE_OF_LIFE"
    );

    @Test
    void correctNumberOfTreeGrowers() {
        long growerCount = Arrays.stream(ModTreeGrowers.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> TreeGrower.class.isAssignableFrom(f.getType()))
            .count();
        assertEquals(5, growerCount, "Should have exactly 5 tree growers");
    }

    @Test
    void allExpectedGrowersExist() {
        List<String> actualFields = getTreeGrowerFieldNames();
        for (String expected : EXPECTED_GROWERS) {
            assertTrue(actualFields.contains(expected),
                "Missing tree grower: " + expected);
        }
    }

    @Test
    void treeGrowerFieldsArePublicStaticFinal() {
        for (Field field : ModTreeGrowers.class.getDeclaredFields()) {
            if (TreeGrower.class.isAssignableFrom(field.getType())) {
                int mods = field.getModifiers();
                assertTrue(Modifier.isPublic(mods), field.getName() + " should be public");
                assertTrue(Modifier.isStatic(mods), field.getName() + " should be static");
                assertTrue(Modifier.isFinal(mods), field.getName() + " should be final");
            }
        }
    }

    @Test
    void treeGrowerFieldNamesFollowUpperSnakeCaseConvention() {
        getTreeGrowerFieldNames().forEach(name ->
            assertTrue(name.matches("[A-Z][A-Z0-9_]*"),
                "Tree grower name should be UPPER_SNAKE_CASE: " + name));
    }

    @Test
    void noDuplicateTreeGrowerNames() {
        List<String> names = getTreeGrowerFieldNames();
        assertEquals(names.size(), names.stream().distinct().count(),
            "Tree grower names should be unique");
    }

    @Test
    void registerMethodExists() throws NoSuchMethodException {
        Method method = ModTreeGrowers.class.getMethod("register");
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void treeGrowersAreNotNull() throws IllegalAccessException {
        for (Field field : ModTreeGrowers.class.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && TreeGrower.class.isAssignableFrom(field.getType())) {
                assertNotNull(field.get(null), field.getName() + " should not be null");
            }
        }
    }

    private List<String> getTreeGrowerFieldNames() {
        return Arrays.stream(ModTreeGrowers.class.getDeclaredFields())
            .filter(f -> Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()))
            .filter(f -> TreeGrower.class.isAssignableFrom(f.getType()))
            .map(Field::getName)
            .toList();
    }
}
