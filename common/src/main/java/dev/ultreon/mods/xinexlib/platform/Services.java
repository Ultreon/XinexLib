package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.ModPlatform;
import dev.ultreon.mods.xinexlib.components.ComponentManager;
import dev.ultreon.mods.xinexlib.network.NetworkRegistry;
import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.platform.services.Platform;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import dev.ultreon.mods.xinexlib.tabs.CreativeModeTabBuilder;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;
import java.util.function.Consumer;

/*
 Service loaders are a built-in Java feature that allow us to locate implementations of an interface that vary from one
 environment to another. In the context of MultiLoader we use this feature to access a mock API in the common code that
 is swapped out for the platform specific implementation at runtime.
*/
@Deprecated
public class Services {
    private Services() {

    }

    /*
     In this example we provide a platform helper which provides information about what platform the mod is running on.
     For example this can be used to check if the code is running on Forge vs Fabric, or to ask the modloader if another
     mod is loaded.
    */
    public static final Platform PLATFORM = XinexPlatform.PLATFORM;

    /*
     This code is used to load a service for the current environment. Your implementation of the service must be defined
     manually by including a text file in META-INF/services named with the fully qualified class name of the service.
     Inside the file you should write the fully qualified class name of the implementation to load for the platform. For
     example our file on Forge points to ForgePlatformHelper while Fabric points to FabricPlatformHelper.
    */
    @Deprecated
    @ApiStatus.Internal
    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    @Deprecated
    public static RegistrarManager getRegistrarManager(String modId) {
        return PLATFORM.getRegistrarManager(modId);
    }

    @Deprecated
    public static boolean isModLoaded(String modId) {
        return PLATFORM.isModLoaded(modId);
    }

    @Deprecated
    public static boolean isDevelopmentEnvironment() {
        return PLATFORM.isDevelopmentEnvironment();
    }

    @Deprecated
    public static String getEnvironmentName() {
        return PLATFORM.getEnvironmentName();
    }

    @Deprecated
    public static ModPlatform getPlatformName() {
        return PLATFORM.getPlatformName();
    }

    @Deprecated
    public static ComponentManager getComponentManager(String modId) {
        return PLATFORM.getComponentManager(modId);
    }

    @Deprecated
    public static CreativeModeTabBuilder creativeTabBuilder() {
        return PLATFORM.creativeTabBuilder();
    }

    @Deprecated
    public static Env getEnv() {
        return PLATFORM.getEnv();
    }

    @Deprecated
    public static Networker createNetworker(String modId, Consumer<NetworkRegistry> registrant) {
        return PLATFORM.createNetworker(modId, registrant);
    }

    @Deprecated
    public static void registerCommand(CommandRegistrant registrant) {
        PLATFORM.registerCommand(registrant);
    }
}
