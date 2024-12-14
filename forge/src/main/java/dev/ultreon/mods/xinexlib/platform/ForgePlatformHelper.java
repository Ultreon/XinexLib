package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.network.ForgeNetworker;
import dev.ultreon.mods.xinexlib.network.NetworkRegistry;
import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.PlatformHelper;
import dev.ultreon.mods.xinexlib.registrar.ForgeRegistrarManager;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.ForgeCreativeTabBuilder;
import dev.ultreon.mods.xinexlib.tabs.CreativeModeTabBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ForgePlatformHelper implements PlatformHelper {
    private final List<CommandRegistrant> registrants = new ArrayList<>();
    private final Map<String, RegistrarManager> registrars = new HashMap<>();
    private ClientPlatformHelper client;

    public ForgePlatformHelper() {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> this.client = new ForgeClientPlatformHelper());

        MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> {
            var dispatcher = event.getDispatcher();
            var buildContext = event.getBuildContext();
            var selection = event.getCommandSelection();

            this.registrants.forEach(registrant -> registrant.register(dispatcher, buildContext, selection));
        });
    }

    @Override
    public ModPlatform getPlatformName() {
        return ModPlatform.Forge;
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
        if (registrars.containsKey(modId)) {
            return this.registrars.get(modId);
        }

        this.registrars.put(modId, new ForgeRegistrarManager(modId));
        return this.registrars.get(modId);
    }

    @Override
    public CreativeModeTabBuilder creativeTabBuilder() {
        return new ForgeCreativeTabBuilder();
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
        return new ForgeNetworker(modId, registrant);
    }

    @Override
    public void registerCommand(CommandRegistrant registrant) {
        this.registrants.add(registrant);
    }

    @Override
    public ClientPlatformHelper client() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            return client;
        }
        throw new IllegalStateException("This method should only be called on the client");
    }
}
