package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.client.FabricEntityRendererRegistry;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatform;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;

public class FabricClientPlatform implements ClientPlatform {
    private final EntityRendererRegistry entityRenderers = new FabricEntityRendererRegistry();

    @Override
    public EntityRendererRegistry entityRenderers() {
        return entityRenderers;
    }

}
