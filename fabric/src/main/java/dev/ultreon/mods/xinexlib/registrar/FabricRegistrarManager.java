package dev.ultreon.mods.xinexlib.registrar;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricRegistrarManager implements RegistrarManager {
    private final String modId;

    public FabricRegistrarManager(String modId) {
        this.modId = modId;
    }

    @Override
    public <T> Registrar<T> getRegistrar(ResourceKey<Registry<T>> key) {
        return new FabricRegistrar<>(key, modId);
    }
}
