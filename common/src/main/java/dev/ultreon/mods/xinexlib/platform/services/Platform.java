package dev.ultreon.mods.xinexlib.platform.services;

import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.components.SimpleComponentManager;
import dev.ultreon.mods.xinexlib.components.ComponentManager;
import dev.ultreon.mods.xinexlib.network.NetworkRegistry;
import dev.ultreon.mods.xinexlib.platform.CommandRegistrant;
import dev.ultreon.mods.xinexlib.platform.Mod;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.CreativeModeTabBuilder;

import java.util.Optional;
import java.util.function.Consumer;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public interface Platform {

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
    RegistrarManager getRegistrarManager(String modId);

    /// Gets the component manager for the given mod id
    ///
    /// @param modId The mod id
    /// @return The component manager
    default ComponentManager getComponentManager(String modId) {
        return new SimpleComponentManager(modId);
    }

    CreativeModeTabBuilder creativeTabBuilder();

    Env getEnv();

    Networker createNetworker(String modId, Consumer<NetworkRegistry> registrant);

    void registerCommand(CommandRegistrant registrant);

    ClientPlatform client();

    Optional<Mod> getMod(String modId);
}
