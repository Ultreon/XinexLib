package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.client.ForgeEntityRendererRegistry;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;

public class ForgeClientPlatformHelper implements ClientPlatformHelper {
    private final EntityRendererRegistry entityRenderers = new ForgeEntityRendererRegistry();

    @Override
    public EntityRendererRegistry entityRenderers() {
        return entityRenderers;
    }
}
