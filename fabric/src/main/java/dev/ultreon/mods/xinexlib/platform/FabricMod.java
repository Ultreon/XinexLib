package dev.ultreon.mods.xinexlib.platform;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;

public class FabricMod implements Mod {
    private final ModMetadata meta;

    public FabricMod(ModContainer container) {
        this.meta = container.getMetadata();
    }

    @Override
    public String getModId() {
        return meta.getId();
    }

    @Override
    public String getName() {
        return meta.getName();
    }

    @Override
    public String getVersion() {
        return meta.getVersion().getFriendlyString();
    }

    @Override
    public String getDescription() {
        return meta.getDescription();
    }

}
