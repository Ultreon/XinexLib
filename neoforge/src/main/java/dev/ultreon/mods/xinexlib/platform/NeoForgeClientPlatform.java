package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.client.NeoForgeEntityRendererRegistry;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatform;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;

public class NeoForgeClientPlatform implements ClientPlatform {
    private final EntityRendererRegistry entityRenderers = new NeoForgeEntityRendererRegistry();

    @Override
    public EntityRendererRegistry entityRenderers() {
        return entityRenderers;
    }

}
