package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.services.IRegistrar;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistrarManager implements IRegistrarManager {
    private final String modId;
    private final IEventBus modEventBus;

    public NeoForgeRegistrarManager(String modId, IEventBus modEventBus) {
        this.modId = modId;
        this.modEventBus = modEventBus;
    }

    @Override
    public <T> IRegistrar<T> getRegistrar(ResourceKey<Registry<T>> key) {
        return new NeoForgeRegistrar<>(DeferredRegister.create(key, modId), modEventBus);
    }
}
