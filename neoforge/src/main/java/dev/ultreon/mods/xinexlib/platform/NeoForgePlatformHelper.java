package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.platform.services.IPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.ICreativeModeTabBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

import java.util.HashMap;

public class NeoForgePlatformHelper implements IPlatformHelper {
    private final HashMap<String, IRegistrarManager> registrars = new HashMap<>();

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

    public void registerMod(String modId, IEventBus modEventBus) {
        this.registrars.put(modId, new NeoForgeRegistrarManager(modId, modEventBus));
    }
}
