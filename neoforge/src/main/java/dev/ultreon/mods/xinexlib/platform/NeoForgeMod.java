package dev.ultreon.mods.xinexlib.platform;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.i18n.MavenVersionTranslator;

public class NeoForgeMod implements Mod {
    private final ModContainer container;

    public NeoForgeMod(ModContainer container) {
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
        return MavenVersionTranslator.artifactVersionToString(container.getModInfo().getVersion());
    }

    @Override
    public String getDescription() {
        return container.getModInfo().getDescription();
    }
}
