package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.network.INetworkRegistry;
import dev.ultreon.mods.xinexlib.network.INetworker;
import dev.ultreon.mods.xinexlib.network.NeoForgeNetworker;
import dev.ultreon.mods.xinexlib.platform.services.IPlatformHelper;
import dev.ultreon.mods.xinexlib.registrar.IRegistrarManager;
import dev.ultreon.mods.xinexlib.registrar.NeoForgeRegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.ICreativeModeTabBuilder;
import dev.ultreon.mods.xinexlib.tabs.NeoForgeCreativeTabBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class NeoForgePlatformHelper implements IPlatformHelper {
    private final HashMap<String, IRegistrarManager> registrars = new HashMap<>();
    private final List<ICommandRegistrant> registrants = new ArrayList<>();
    private IEventBus modEventBus;

    public NeoForgePlatformHelper() {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> {
            for (ICommandRegistrant registrant : this.registrants) {
                registrant.register(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
            }
        });
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
    public IRegistrarManager getRegistrarManager(String modId) {
        IRegistrarManager iRegistrarManager = this.registrars.get(modId);
        if (iRegistrarManager == null) {
            throw new IllegalStateException("No registrar manager found for mod " + modId + " did you register the mod?");
        }
        return iRegistrarManager;
    }

    @Override
    public ICreativeModeTabBuilder creativeTabBuilder() {
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
    public INetworker createNetworker(String modId, Consumer<INetworkRegistry> registrant) {
        return new NeoForgeNetworker(modEventBus, modId, registrant);
    }

    @Override
    public void registerCommand(ICommandRegistrant registrant) {
        this.registrants.add(registrant);
    }

    public void registerMod(String modId, IEventBus modEventBus) {
        this.modEventBus = modEventBus;
        this.registrars.put(modId, new NeoForgeRegistrarManager(modId, modEventBus));
    }
}
