package dev.ultreon.mods.xinexlib.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.client.render.model.TestEntityModel;
import dev.ultreon.mods.xinexlib.dev.entity.TestEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class TestEntityRenderer extends EntityRenderer<TestEntity, TestEntityRenderState> {
    private final TestEntityModel<TestEntityRenderState> model;
    private final net.minecraft.client.renderer.rendertype.RenderType renderType = RenderTypes.eyes(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/test_entity.png"));

    public TestEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);

        this.model = new TestEntityModel(pContext.bakeLayer(TestEntityModel.LAYER_LOCATION));
    }

    @Override
    public @NonNull TestEntityRenderState createRenderState() {
        return new TestEntityRenderState();
    }

    public void submit(@NonNull TestEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        submitNodeCollector.submitModel(
                this.model, state, poseStack, this.getTextureLocation(state), state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
        );
        poseStack.popPose();
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    private Identifier getTextureLocation(TestEntityRenderState state) {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/test_entity.png");
    }
}
