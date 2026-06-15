package com.verdantrealms.util;

import com.verdantrealms.VerdantRealms;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class RegistryHelper {

    private RegistryHelper() {}

    public static ResourceLocation modLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(VerdantRealms.MOD_ID, name);
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registryKey, String name) {
        return ResourceKey.create(registryKey, modLocation(name));
    }
}
