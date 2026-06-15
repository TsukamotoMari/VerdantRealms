package com.verdantrealms.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class ModWorldGenProviderTest {

    @Test
    void classExtendsDatapackBuiltinEntriesProvider() {
        assertTrue(DatapackBuiltinEntriesProvider.class.isAssignableFrom(ModWorldGenProvider.class),
            "ModWorldGenProvider should extend DatapackBuiltinEntriesProvider");
    }

    @Test
    void builderFieldExists() throws NoSuchFieldException {
        Field field = ModWorldGenProvider.class.getDeclaredField("BUILDER");
        assertNotNull(field);
        int mods = field.getModifiers();
        assertTrue(Modifier.isPublic(mods), "BUILDER should be public");
        assertTrue(Modifier.isStatic(mods), "BUILDER should be static");
        assertTrue(Modifier.isFinal(mods), "BUILDER should be final");
    }

    @Test
    void builderIsRegistrySetBuilder() throws NoSuchFieldException {
        Field field = ModWorldGenProvider.class.getDeclaredField("BUILDER");
        assertEquals(RegistrySetBuilder.class, field.getType());
    }

    @Test
    void builderIsNotNull() {
        assertNotNull(ModWorldGenProvider.BUILDER,
            "BUILDER should be initialized");
    }

    @Test
    void constructorAcceptsCorrectParameters() throws NoSuchMethodException {
        assertNotNull(ModWorldGenProvider.class.getConstructor(
            net.minecraft.data.PackOutput.class,
            java.util.concurrent.CompletableFuture.class
        ));
    }
}
