package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.platform.services.IPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHelper implements IPlatformHelper {

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
    public IRegistrarManager getRegistrarManager(String modId) {
        return new FabricRegistrarManager(modId);
    }
}
