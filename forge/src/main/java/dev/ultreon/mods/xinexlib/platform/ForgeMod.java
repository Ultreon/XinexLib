package dev.ultreon.mods.xinexlib.platform;

import net.minecraftforge.common.util.MavenVersionStringHelper;
import net.minecraftforge.fml.ModContainer;

public class ForgeMod implements Mod {
    private final ModContainer container;

    public ForgeMod(ModContainer container) {
        this.container = container;
    }

    @Override
    public String getModId() {
        return container.getModId();
    }

    @Override
    public String getName() {
        return container.getModInfo().getDisplayName();
    }

    @Override
    public String getVersion() {
        return MavenVersionStringHelper.artifactVersionToString(container.getModInfo().getVersion());
    }

    @Override
    public String getDescription() {
        return container.getModInfo().getDescription();
    }

}
