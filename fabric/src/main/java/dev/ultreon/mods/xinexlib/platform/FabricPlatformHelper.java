package dev.ultreon.mods.xinexlib.platform;

import com.mojang.brigadier.CommandDispatcher;
import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.network.FabricNetworker;
import dev.ultreon.mods.xinexlib.network.NetworkRegistry;
import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.PlatformHelper;
import dev.ultreon.mods.xinexlib.registrar.FabricRegistrarManager;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.FabricCreativeTabBuilder;
import dev.ultreon.mods.xinexlib.tabs.CreativeModeTabBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FabricPlatformHelper implements PlatformHelper {
    private final List<CommandRegistrant> commandRegistrants = new ArrayList<>();
    private final Map<String, RegistrarManager> registrars = new HashMap<>();
    private ClientPlatformHelper client;

    public FabricPlatformHelper() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            client = new FabricClientPlatformHelper();
        }

        CommandRegistrationCallback.EVENT.register(this::register);
    }

    @Override
    public ModPlatform getPlatformName() {
        return ModPlatform.Fabric;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public RegistrarManager getRegistrarManager(String modId) {
        if (this.registrars.containsKey(modId)) {
            return this.registrars.get(modId);
        }

        this.registrars.put(modId, new FabricRegistrarManager(modId));
        return this.registrars.get(modId);
    }

    @Override
    public CreativeModeTabBuilder creativeTabBuilder() {
        return new FabricCreativeTabBuilder();
    }

    @Override
    public Env getEnv() {
        return switch (FabricLoader.getInstance().getEnvironmentType()) {
            case CLIENT -> Env.CLIENT;
            case SERVER -> Env.SERVER;
        };
    }

    @Override
    public Networker createNetworker(String modId, Consumer<NetworkRegistry> registrant) {
        return new FabricNetworker(modId, registrant);
    }

    @Override
    public void registerCommand(CommandRegistrant registrant) {
        this.commandRegistrants.add(registrant);
    }

    @Override
    public ClientPlatformHelper client() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            return client;
        }
        throw new IllegalStateException("This method should only be called on the client");
    }

    private void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
        for (CommandRegistrant registrant : commandRegistrants) {
            registrant.register(dispatcher, registryAccess, environment);
        }
    }
}
