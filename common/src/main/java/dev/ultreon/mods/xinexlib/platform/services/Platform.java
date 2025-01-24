package dev.ultreon.mods.xinexlib.platform.services;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.components.ComponentManager;
import dev.ultreon.mods.xinexlib.network.NetworkRegistry;
import dev.ultreon.mods.xinexlib.platform.CommandRegistrant;
import dev.ultreon.mods.xinexlib.platform.Mod;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.CreativeModeTabBuilder;
import net.minecraft.world.item.CreativeModeTab;

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
    ComponentManager getComponentManager(String modId);

    /// Creates a new creative mode tab builder
    ///
    /// @see CreativeModeTab
    /// @return The creative mode tab builder
    CreativeModeTabBuilder creativeTabBuilder();

    /// Gets the current environment
    ///
    /// @return The current environment
    Env getEnv();

    /// Creates a new networker. This generally only needs to be created once per mod. Or not at all.
    ///
    /// @param modId The mod id
    /// @param registrant The network registry
    /// @return A new networker instance.
    Networker createNetworker(String modId, Consumer<NetworkRegistry> registrant);

    /// Registers a new command.
    ///
    /// @param registrant The command
    /// @see Command
    void registerCommand(CommandRegistrant registrant);

    /// Gets the current client-side platform
    ClientPlatform client();

    /// Used for getting information about a mod from the mod list.
    ///
    /// @param modId The mod id
    /// @return The mod information or an empty optional
    Optional<Mod> getMod(String modId);
}
