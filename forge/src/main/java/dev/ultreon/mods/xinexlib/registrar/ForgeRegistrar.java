package dev.ultreon.mods.xinexlib.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class ForgeRegistrar<T> implements Registrar<T> {
    private final DeferredRegister<T> deferredRegister;
    private final IEventBus modEventBus;
    private final String namespace;
    private Registry<T> registry;
    private final List<RegistrySupplier<? extends T, T>> registryObjects = new ArrayList<>();

    public ForgeRegistrar(DeferredRegister<T> deferredRegister, IEventBus modEventBus, String namespace) {
        this.deferredRegister = deferredRegister;
        this.modEventBus = modEventBus;
        this.namespace = namespace;
    }

    @Override
    @SuppressWarnings("unchecked")

    public <R extends T> RegistrySupplier<R, T> register(String name, Supplier<R> supplier) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, name);
        ForgeRegistrySupplier<R, T> rForgeRegistrySupplier = new ForgeRegistrySupplier<>(deferredRegister.register(name, supplier), ResourceKey.<R>create((ResourceKey) deferredRegister.getRegistryKey(), resourceLocation), this);
        this.registryObjects.add(rForgeRegistrySupplier);
        return rForgeRegistrySupplier;
    }

    @Override
    public void load() {
        this.deferredRegister.register(modEventBus);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Registry<T> registry() {
        if (this.registry == null) {
            this.registry = BuiltInRegistries.REGISTRY.get((ResourceKey) deferredRegister.getRegistryKey());
            if (this.registry == null) {
                throw new IllegalStateException("Registry " + deferredRegister.getRegistryKey() + " does not exist!");
            }
        }
        return this.registry;
    }

    @Override
    public @NotNull Iterator<RegistrySupplier<?, T>> iterator() {
        return registryObjects.iterator();
    }
}
