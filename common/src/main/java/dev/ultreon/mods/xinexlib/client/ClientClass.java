package dev.ultreon.mods.xinexlib.client;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.EnvExecutor;
import dev.ultreon.mods.xinexlib.client.event.LocalPlayerQuitEvent;
import dev.ultreon.mods.xinexlib.client.render.TestEntityRenderer;
import dev.ultreon.mods.xinexlib.client.render.model.TestEntityModel;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.dev.DevEntities;
import dev.ultreon.mods.xinexlib.platform.Services;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatformHelper;
import net.minecraft.client.Minecraft;

public class ClientClass {
    public static void init() {
        if (Services.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            initDev();
        }
    }

    private static void initDev() {
        ClientPlatformHelper client = Services.PLATFORM.client();
        client.entityRenderers().register(DevEntities.TEST::get, TestEntityRenderer::new);
        client.entityRenderers().registerModel(TestEntityModel.LAYER_LOCATION, TestEntityModel::createBodyLayer);
    }

    public static void onDisconnect() {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> EventSystem.MAIN.publish(new LocalPlayerQuitEvent(Minecraft.getInstance().player)));
    }
}
