package dev.ultreon.mods.xinexlib.platform;

import com.mojang.brigadier.CommandDispatcher;
import dev.ultreon.mods.xinexlib.platform.services.Platform;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/// A command registrant for a mod
/// This is used to register commands for a mod to Minecraft's command dispatcher.
///
/// @see Platform#registerCommand(CommandRegistrant)
/// @author <a href="https://github.com/XyperCode">XyperCode</a>
/// @since 0.1.0
public interface CommandRegistrant {
    /// Registers commands to Minecraft's command dispatcher
    /// This is often implemented by a mod using the {@link Platform#registerCommand(CommandRegistrant)} method
    ///
    /// @param dispatcher The command dispatcher
    /// @param registryAccess The command registry access
    /// @param environment The command environment
    void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment);
}
