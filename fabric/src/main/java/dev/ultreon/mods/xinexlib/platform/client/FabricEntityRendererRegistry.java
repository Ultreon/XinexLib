package dev.ultreon.mods.xinexlib.platform.client;

import dev.ultreon.mods.xinexlib.client.LayerDefinitionProvider;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class FabricEntityRendererRegistry implements EntityRendererRegistry {
    @Override
    public <T extends BlockEntity, S extends BlockEntityRenderState> void register(Holder<BlockEntityType<T>> entity, BlockEntityRendererProvider<T, S> provider) {
        BlockEntityRenderers.register(entity.value(), provider);
    }

    @Override
    public <T extends Entity> void register(Supplier<EntityType<T>> entity, EntityRendererProvider<T> provider) {
        EntityRenderers.register(entity.get(), provider);
    }

    @Override
    public <T extends Entity> void registerModel(ModelLayerLocation location, LayerDefinitionProvider provider) {
        ModelLayerRegistry.registerModelLayer(location, provider::createBodyLayer);
    }
}
