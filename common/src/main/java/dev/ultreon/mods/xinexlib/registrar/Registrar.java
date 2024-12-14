package dev.ultreon.mods.xinexlib.registrar;

import net.minecraft.core.Registry;

import java.util.function.Supplier;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public interface Registrar<T> extends Iterable<RegistrySupplier<?, T>> {
    <R extends T> RegistrySupplier<R, T> register(String name, Supplier<R> supplier);

    void load();

    Registry<T> registry();
}
