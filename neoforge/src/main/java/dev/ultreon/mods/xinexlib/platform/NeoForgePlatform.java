package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.network.NetworkRegistry;
import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.network.NeoForgeNetworker;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatform;
import dev.ultreon.mods.xinexlib.platform.services.Platform;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import dev.ultreon.mods.xinexlib.registrar.NeoForgeRegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.CreativeModeTabBuilder;
import dev.ultreon.mods.xinexlib.tabs.NeoForgeCreativeTabBuilder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class NeoForgePlatform implements Platform {
    public static NeoForgePlatform platform;
    private final HashMap<String, RegistrarManager> registrars = new HashMap<>();
    private final List<CommandRegistrant> registrants = new ArrayList<>();
    private IEventBus modEventBus;
    private ClientPlatform client;

    public NeoForgePlatform() {
        platform = this;

        if (FMLEnvironment.dist == Dist.CLIENT) {
            client = new NeoForgeClientPlatform();
        }

        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
            for (CommandRegistrant registrant : this.registrants) {
                registrant.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
            }
        });
    }

    public static NeoForgePlatform getPlatform() {
        return (NeoForgePlatform) XinexPlatform.PLATFORM;
    }

    @Override
    public ModPlatform getPlatformName() {
        return ModPlatform.NeoForge;
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public RegistrarManager getRegistrarManager(String modId) {
        RegistrarManager registrarManager = this.registrars.get(modId);
        if (registrarManager == null) {
            throw new IllegalStateException("No registrar manager found for mod " + modId + " did you register the mod?");
        }
        return registrarManager;
    }

    @Override
    public CreativeModeTabBuilder creativeTabBuilder() {
        return new NeoForgeCreativeTabBuilder();
    }

    @Override
    public Env getEnv() {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> Env.CLIENT;
            case DEDICATED_SERVER -> Env.SERVER;
        };
    }

    @Override
    public Networker createNetworker(String modId, Consumer<NetworkRegistry> registrant) {
        return new NeoForgeNetworker(modEventBus, modId, registrant);
    }

    @Override
    public void registerCommand(CommandRegistrant registrant) {
        this.registrants.add(registrant);
    }

    @Override
    public ClientPlatform client() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return client;
        }
        throw new IllegalStateException("This method should only be called on the client");
    }

    @Override
    public Optional<Mod> getMod(String modId) {
        return ModList.get().getModContainerById(modId).map(NeoForgeMod::new);
    }

    public void registerMod(String modId, IEventBus modEventBus) {
        this.modEventBus = modEventBus;
        this.registrars.put(modId, new NeoForgeRegistrarManager(modId, modEventBus));
    }
}
