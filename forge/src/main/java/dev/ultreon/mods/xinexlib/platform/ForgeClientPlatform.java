package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.client.ForgeEntityRendererRegistry;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatform;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;

public class ForgeClientPlatform implements ClientPlatform {
    private final EntityRendererRegistry entityRenderers = new ForgeEntityRendererRegistry();

    @Override
    public EntityRendererRegistry entityRenderers() {
        return entityRenderers;
    }
}
