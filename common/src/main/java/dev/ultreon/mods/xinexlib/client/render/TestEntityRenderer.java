package dev.ultreon.mods.xinexlib.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.client.render.model.TestEntityModel;
import dev.ultreon.mods.xinexlib.dev.entity.TestEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TestEntityRenderer extends EntityRenderer<TestEntity> {
    private final TestEntityModel model;
    private final RenderType renderType = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/test_entity.png"));

    public TestEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);

        this.model = new TestEntityModel(pContext.bakeLayer(TestEntityModel.LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull TestEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model.setupAnim(entity, entity.getYRot(), entity.getXRot(), entity.tickCount + partialTick, entity.getYHeadRot(), entity.getXRot());
        this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.renderType), packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull TestEntity testEntity) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/test_entity.png");
    }
}
