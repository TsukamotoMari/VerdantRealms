package com.verdantrealms;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class VerdantRealmsTest {

    @Test
    void modIdConstantIsCorrect() {
        assertEquals("verdantrealms", VerdantRealms.MOD_ID);
    }

    @Test
    void modIdIsNotEmpty() {
        assertNotNull(VerdantRealms.MOD_ID);
        assertFalse(VerdantRealms.MOD_ID.isEmpty());
    }

    @Test
    void modIdIsLowercaseAlphanumeric() {
        assertTrue(VerdantRealms.MOD_ID.matches("[a-z0-9_]+"),
            "Mod ID should be lowercase alphanumeric (NeoForge convention)");
    }

    @Test
    void loggerIsInitialized() {
        assertNotNull(VerdantRealms.LOGGER);
    }

    @Test
    void loggerHasCorrectName() {
        assertEquals(VerdantRealms.MOD_ID, VerdantRealms.LOGGER.getName());
    }

    @Test
    void classHasModAnnotation() {
        assertTrue(VerdantRealms.class.isAnnotationPresent(net.neoforged.fml.common.Mod.class),
            "Main class must have @Mod annotation");
    }

    @Test
    void modAnnotationValueMatchesModId() {
        net.neoforged.fml.common.Mod annotation = VerdantRealms.class.getAnnotation(net.neoforged.fml.common.Mod.class);
        assertNotNull(annotation);
        assertEquals(VerdantRealms.MOD_ID, annotation.value());
    }

    @Test
    void modIdFieldIsPublicStaticFinal() throws NoSuchFieldException {
        Field field = VerdantRealms.class.getDeclaredField("MOD_ID");
        int modifiers = field.getModifiers();
        assertTrue(Modifier.isPublic(modifiers), "MOD_ID should be public");
        assertTrue(Modifier.isStatic(modifiers), "MOD_ID should be static");
        assertTrue(Modifier.isFinal(modifiers), "MOD_ID should be final");
    }

    @Test
    void loggerFieldIsPublicStaticFinal() throws NoSuchFieldException {
        Field field = VerdantRealms.class.getDeclaredField("LOGGER");
        int modifiers = field.getModifiers();
        assertTrue(Modifier.isPublic(modifiers), "LOGGER should be public");
        assertTrue(Modifier.isStatic(modifiers), "LOGGER should be static");
        assertTrue(Modifier.isFinal(modifiers), "LOGGER should be final");
    }

    @Test
    void constructorAcceptsIEventBus() throws NoSuchMethodException {
        assertNotNull(VerdantRealms.class.getConstructor(net.neoforged.bus.api.IEventBus.class),
            "Constructor must accept IEventBus parameter");
    }
}
