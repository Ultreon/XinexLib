package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.client.ClientClass;
import dev.ultreon.mods.xinexlib.platform.Services;

public class XinexLibClient {
    private XinexLibClient() {

    }

    public static void init() {
        ClientClass.init();
        if (Services.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            XinexLibClient.initDev();
        }
    }

    private static void initDev() {

    }
}
