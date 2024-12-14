package dev.ultreon.mods.xinexlib.client;

import net.minecraft.client.model.geom.builders.LayerDefinition;

@FunctionalInterface
public interface LayerDefinitionProvider {
    LayerDefinition createBodyLayer();
}