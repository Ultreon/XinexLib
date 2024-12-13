package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.network.ForgeNetworker;
import dev.ultreon.mods.xinexlib.network.INetworkRegistry;
import dev.ultreon.mods.xinexlib.network.INetworker;
import dev.ultreon.mods.xinexlib.platform.services.IPlatformHelper;
import dev.ultreon.mods.xinexlib.registrar.ForgeRegistrarManager;
import dev.ultreon.mods.xinexlib.registrar.IRegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.ForgeCreativeTabBuilder;
import dev.ultreon.mods.xinexlib.tabs.ICreativeModeTabBuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForgePlatformHelper implements IPlatformHelper {
    private final List<ICommandRegistrant> registrants = new ArrayList<>();

    public ForgePlatformHelper() {
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
    public IRegistrarManager getRegistrarManager(String modId) {
        return new ForgeRegistrarManager(modId);
    }

    @Override
    public ICreativeModeTabBuilder creativeTabBuilder() {
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
    public INetworker createNetworker(String modId, Consumer<INetworkRegistry> registrant) {
        return new ForgeNetworker(modId, registrant);
    }

    @Override
    public void registerCommand(ICommandRegistrant registrant) {
        this.registrants.add(registrant);
    }
}
