package dev.ultreon.mods.xinexlib.platform.services;

import dev.ultreon.mods.xinexlib.client.LayerDefinitionProvider;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface EntityRendererRegistry {
    <T extends BlockEntity> void register(Holder<BlockEntityType<T>> entity, BlockEntityRendererProvider<T> provider);

    <T extends Entity> void register(Supplier<EntityType<T>> entity, EntityRendererProvider<T> provider);

    <T extends Entity> void registerModel(ModelLayerLocation location, LayerDefinitionProvider provider);
}
