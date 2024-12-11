package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.services.IRegistrar;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class NeoForgeRegistrar<T> implements IRegistrar<T> {
    private final DeferredRegister<T> deferredRegister;
    private final IEventBus modEventBus;
    private boolean registered = false;
    private final List<IRegistrySupplier<? extends T, T>> values = new ArrayList<>();

    public NeoForgeRegistrar(DeferredRegister<T> deferredRegister, IEventBus modEventBus) {
        this.deferredRegister = deferredRegister;
        this.modEventBus = modEventBus;
    }

    @Override
    public <R extends T> IRegistrySupplier<R, T> register(String name, Supplier<R> supplier) {
        NeoForgeRegistrySupplier<R, T> rtNeoForgeRegistrySupplier = new NeoForgeRegistrySupplier<>(deferredRegister.register(name, supplier), this, ResourceLocation.fromNamespaceAndPath(deferredRegister.getNamespace(), name));
        this.values.add(rtNeoForgeRegistrySupplier);
        return rtNeoForgeRegistrySupplier;
    }

    @Override
    public void load() {
        if (this.registered) throw new IllegalStateException("Deferred register has already been registered");
        this.registered = true;
        this.deferredRegister.register(modEventBus);
    }

    @Override
    public Registry<T> registry() {
        return this.deferredRegister.getRegistry().get();
    }

    @Override
    public @NotNull Iterator<IRegistrySupplier<?, T>> iterator() {
        return this.values.iterator();
    }
}
