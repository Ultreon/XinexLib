package dev.ultreon.mods.xinexlib.client;

import net.fabricmc.api.ClientModInitializer;

public class FabricXinexLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        XinexLibClient.init();
    }
}
