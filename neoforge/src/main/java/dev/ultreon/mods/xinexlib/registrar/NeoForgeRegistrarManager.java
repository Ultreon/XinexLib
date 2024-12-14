package dev.ultreon.mods.xinexlib.registrar;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NeoForgeRegistrarManager implements RegistrarManager {
    private final String modId;
    private final IEventBus modEventBus;

    public NeoForgeRegistrarManager(String modId, IEventBus modEventBus) {
        this.modId = modId;
        this.modEventBus = modEventBus;
    }

    @Override
    public <T> Registrar<T> getRegistrar(ResourceKey<Registry<T>> key) {
        return new NeoForgeRegistrar<>(DeferredRegister.create(key, modId), modEventBus);
    }
}
