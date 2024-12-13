package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.platform.services.IPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.ICreativeModeTabBuilder;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

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
}
