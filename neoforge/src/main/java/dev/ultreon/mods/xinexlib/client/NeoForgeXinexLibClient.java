package dev.ultreon.mods.xinexlib.client;

import dev.ultreon.mods.xinexlib.client.event.screen.ClientScreenOpenEvent;
import dev.ultreon.mods.xinexlib.client.event.screen.ClientScreenPreInitEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.platform.XinexPlatform;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

public class NeoForgeXinexLibClient {
    private NeoForgeXinexLibClient() {

    }

    public static void init() {
        NeoForgeXinexLibClient client = new NeoForgeXinexLibClient();
        NeoForge.EVENT_BUS.register(client);

        XinexLibClient.init();
        if (XinexPlatform.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            client.initDev();
        }
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

    private void initDev() {

    }
}
