package dev.ultreon.mods.xinexlib.platform.services;

import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.components.ComponentManager;
import dev.ultreon.mods.xinexlib.components.IComponentManager;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public interface IPlatformHelper {

    /// Gets the name of the current platform
    ///
    /// @return The name of the current platform.
    ModPlatform getPlatformName();

    /// Checks if a mod with the given id is loaded.
    ///
    /// @param modId The mod to check if it is loaded.
    /// @return True if the mod is loaded, false otherwise.
    boolean isModLoaded(String modId);

    /// Check if the game is currently in a development environment.
    ///
    /// @return True if in a development environment, false otherwise.
    boolean isDevelopmentEnvironment();

    /// Gets the name of the environment type as a string.
    ///
    /// @return The name of the environment type.
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /// Gets the registrar manager for the given mod id
    ///
    /// @param modId The mod id
    /// @return The registrar manager
    IRegistrarManager getRegistrarManager(String modId);

    /// Gets the component manager for the given mod id
    ///
    /// @param modId The mod id
    /// @return The component manager
    default IComponentManager getComponentManager(String modId) {
        return new ComponentManager(modId);
    }
}
