package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.services.IRegistrar;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricRegistrarManager implements IRegistrarManager {
    private final String modId;

    public FabricRegistrarManager(String modId) {
        this.modId = modId;
    }

    @Override
    public <T> IRegistrar<T> getRegistrar(ResourceKey<Registry<T>> key) {
        return new FabricRegistrar<>(key, modId);
    }
}
