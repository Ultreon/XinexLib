package dev.ultreon.mods.xinexlib.platform.client;

import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.client.LayerDefinitionProvider;
import dev.ultreon.mods.xinexlib.platform.services.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeEntityRendererRegistry implements EntityRendererRegistry {
    private static final Map<Holder<BlockEntityType<?>>, BlockEntityRendererProvider<?>> BLOCK_ENTITY_RENDERERS = new HashMap<>();
    private static final Map<Supplier<EntityType<?>>, EntityRendererProvider<?>> ENTITY_RENDERERS = new HashMap<>();
    private static final Map<ModelLayerLocation, LayerDefinitionProvider> MODEL_LAYERS = new HashMap<>();

    @Override
    public <T extends BlockEntity> void register(Holder<BlockEntityType<T>> entity, BlockEntityRendererProvider<T> provider) {
        //noinspection unchecked
        BLOCK_ENTITY_RENDERERS.put((Holder) entity, provider);
    }

    @Override
    public <T extends Entity> void register(Supplier<EntityType<T>> entity, EntityRendererProvider<T> provider) {
        //noinspection unchecked
        ENTITY_RENDERERS.put((Supplier) entity, provider);
    }
    @Override
    public <T extends Entity> void registerModel(ModelLayerLocation location, LayerDefinitionProvider provider) {
        MODEL_LAYERS.put(location, provider);
    }

    @SubscribeEvent
    public static void onRenderersRegister(EntityRenderersEvent.RegisterRenderers event) {
        for (Map.Entry<Holder<BlockEntityType<? extends BlockEntity>>, BlockEntityRendererProvider<?>> entry : BLOCK_ENTITY_RENDERERS.entrySet()) {
            BlockEntityType<? extends BlockEntity> blockEntityType = entry.getKey().value();
            BlockEntityRendererProvider<?> blockEntityRendererProvider = entry.getValue();

            //noinspection unchecked
            event.registerBlockEntityRenderer(blockEntityType, (BlockEntityRendererProvider<BlockEntity>) blockEntityRendererProvider);
        }

        for (Map.Entry<Supplier<EntityType<?>>, ? extends EntityRendererProvider<?>> entry : ENTITY_RENDERERS.entrySet()) {
            EntityType<?> entityType = entry.getKey().get();
            EntityRendererProvider<?> entityRendererProvider = entry.getValue();

            //noinspection unchecked
            event.registerEntityRenderer(entityType, (EntityRendererProvider<Entity>) entityRendererProvider);
        }
    }

    @SubscribeEvent
    public static void onLayerDefinitionsRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (Map.Entry<ModelLayerLocation, LayerDefinitionProvider> entry : MODEL_LAYERS.entrySet()) {
            ModelLayerLocation location = entry.getKey();
            LayerDefinitionProvider provider = entry.getValue();

            event.registerLayerDefinition(location, provider::createBodyLayer);
        }
    }
}
