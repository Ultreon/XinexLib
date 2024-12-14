package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.client.FabricEntityRendererRegistry;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;

public class FabricClientPlatformHelper implements ClientPlatformHelper {
    private final EntityRendererRegistry entityRenderers = new FabricEntityRendererRegistry();

    @Override
    public EntityRendererRegistry entityRenderers() {
        return entityRenderers;
    }

}
