package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.services.IRegistrar;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

public class ForgeRegistrarManager implements IRegistrarManager {
    private final String modId;
    private final IEventBus modEventBus;

    public ForgeRegistrarManager(String modId) {
        this.modId = modId;
        this.modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }

    @Override
    public <T> IRegistrar<T> getRegistrar(ResourceKey<Registry<T>> key) {
        return new ForgeRegistrar<>(DeferredRegister.create(key, modId), modEventBus, modId);
    }
}
