package dev.ultreon.mods.xinexlib.client;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.EnvExecutor;
import dev.ultreon.mods.xinexlib.client.event.LocalPlayerQuitEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.platform.Services;
import net.minecraft.client.Minecraft;

public class ClientClass {
    public static void init() {
        if (Services.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            initDev();
        }
    }

    private static void initDev() {
        // TODO
    }

    public static void onDisconnect() {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> EventSystem.MAIN.publish(new LocalPlayerQuitEvent(Minecraft.getInstance().player)));
    }
}
