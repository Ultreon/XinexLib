package dev.ultreon.mods.xinexlib.client;

import dev.ultreon.mods.xinexlib.platform.Services;

public class ClientClass {
    public static void init() {
        if (Services.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            initDev();
        }
    }

    private static void initDev() {
        // TODO
    }
}
