package dev.ultreon.mods.xinexlib.registrar;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/// A registry supplier for a registry value
///
/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public interface RegistrySupplier<R extends T, T> extends Holder<T> {
    /// Gets the value if it is bound
    default R get() {
        return asOptional().orElseThrow(() -> new IllegalStateException("Value " + getId() + " in registry " + registry().key().location() + " is not bound!"));
    }

    /// Gets the value if it is bound
    Optional<R> asOptional();

    /// The id of this value
    ResourceLocation getId();

    /// The registry this supplier belongs to
    Registry<T> registry();
}
