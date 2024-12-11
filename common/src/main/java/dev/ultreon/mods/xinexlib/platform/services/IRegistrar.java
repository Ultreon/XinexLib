package dev.ultreon.mods.xinexlib.platform.services;

import net.minecraft.core.Registry;

import java.util.function.Supplier;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public interface IRegistrar<T> extends Iterable<IRegistrySupplier<?, T>> {
    <R extends T> IRegistrySupplier<R, T> register(String name, Supplier<R> supplier);

    void load();

    Registry<T> registry();
}
