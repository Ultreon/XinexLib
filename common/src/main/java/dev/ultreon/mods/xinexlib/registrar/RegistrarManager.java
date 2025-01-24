package dev.ultreon.mods.xinexlib.registrar;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

/// Manages the Minecraft registry system.
///
/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public interface RegistrarManager {
    ///  Gets the registrar for the given registry
    ///
    /// @param key The key of the registry
    /// @return The registrar
    <T> Registrar<T> getRegistrar(ResourceKey<Registry<T>> key);

    /// Creates a new registrar for the given class and resource key
    ///
    /// @param key The key to use for the registry
    /// @param clazz The class which represents the type of the registry
    /// @return The built registrar
    <T> Registrar<T> createRegistrar(ResourceKey<Registry<T>> key, Class<T> clazz);

}
