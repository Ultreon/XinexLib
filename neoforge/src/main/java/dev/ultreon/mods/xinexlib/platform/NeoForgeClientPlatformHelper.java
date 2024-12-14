package dev.ultreon.mods.xinexlib.platform;

import dev.ultreon.mods.xinexlib.platform.client.NeoForgeEntityRendererRegistry;
import dev.ultreon.mods.xinexlib.platform.services.ClientPlatformHelper;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;

public class NeoForgeClientPlatformHelper implements ClientPlatformHelper {
    private final EntityRendererRegistry entityRenderers = new NeoForgeEntityRendererRegistry();

    @Override
    public EntityRendererRegistry entityRenderers() {
        return entityRenderers;
    }

}
