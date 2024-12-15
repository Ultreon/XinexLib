package dev.ultreon.mods.xinexlib.client;

import dev.ultreon.mods.xinexlib.client.event.ClientScreenOpenEvent;
import dev.ultreon.mods.xinexlib.client.event.ClientScreenPreInitEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.platform.XinexPlatform;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeXinexLibClient {
    private ForgeXinexLibClient() {

    }

    public static void clientInit() {
        ForgeXinexLibClient client = new ForgeXinexLibClient();
        if (XinexPlatform.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            client.initDev();
        }
        MinecraftForge.EVENT_BUS.register(client);
        XinexLibClient.init();
    }

    private void initDev() {

    }

    @SubscribeEvent
    public void onScreenOpen(ScreenEvent.Opening event) {
        ClientScreenOpenEvent published = EventSystem.MAIN.publish(new ClientScreenOpenEvent(event.getScreen()));
        if (published.isCanceled()) {
            if (published.get() != null) {
                event.setNewScreen(published.get());
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onScreenPreInit(ScreenEvent.Init.Pre event) {
        EventSystem.MAIN.publish(new ClientScreenPreInitEvent(event.getScreen()));
    }

    @SubscribeEvent
    public void onScreenPostInit(ScreenEvent.Init.Post event) {
        EventSystem.MAIN.publish(new ClientScreenPreInitEvent(event.getScreen()));
    }
}
