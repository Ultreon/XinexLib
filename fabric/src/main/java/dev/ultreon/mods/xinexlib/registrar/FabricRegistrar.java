package dev.ultreon.mods.xinexlib.registrar;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class FabricRegistrar<T> implements Registrar<T> {
    private final ResourceKey<Registry<T>> key;
    private final String modId;
    private Registry<T> registry;
    private final List<RegistrySupplier<?, T>> suppliers = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public FabricRegistrar(ResourceKey<Registry<T>> key, String modId) {
        this.key = key;
        this.modId = modId;
    }

    @Override
    public <R extends T> RegistrySupplier<R, T> register(String name, Supplier<R> supplier) {
        if (registry != null) {
            Identifier Identifier = net.minecraft.resources.Identifier.fromNamespaceAndPath(modId, name);
            FabricRegistrySupplier<R, T> rtFabricRegistrySupplier = new FabricRegistrySupplier<R, T>(this, ResourceKey.<R>create((ResourceKey) key, Identifier)) {
                @Override
                protected void register() {
                    this.value = supplier.get();
                    Registry.register(registry, Identifier.fromNamespaceAndPath(modId, name), this.value);
                }
            };
            this.suppliers.add(rtFabricRegistrySupplier);
            return rtFabricRegistrySupplier;
        } else {
            throw new IllegalStateException("Registry " + key + " does not exist");
        }
    }

    @Override
    public void load() {
        for (RegistrySupplier<?, T> supplier : this.suppliers) {
            FabricRegistrySupplier<?, T> fabricRegistrySupplier = (FabricRegistrySupplier<?, T>) supplier;
            fabricRegistrySupplier.register();
        }
    }

    @Override
    public Registry<T> registry() {
        return registry;
    }

    @Override
    public @NotNull Iterator<RegistrySupplier<?, T>> iterator() {
        return suppliers.iterator();
    }

    public Registrar<T> create() {
        this.registry = FabricRegistryBuilder.create(key).attribute(RegistryAttribute.MODDED).buildAndRegister();
        return this;
    }

    public Registrar<T> retrieve() {
        this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.getOptional((ResourceKey) key).orElseThrow();
        return this;
    }
}
