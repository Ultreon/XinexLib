/*
 Made with Blockbench 4.11.2
 Exported for Minecraft version 1.17 or later with Mojang mappings
 Paste this class into your mod and generate all required imports
*/

package dev.ultreon.mods.xinexlib.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.dev.entity.TestEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class TestEntityModel<T extends TestEntity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "test_entity"), "main");
	private final ModelPart bone;
	private final ModelPart bone2;
	private final ModelPart bone3;

	public TestEntityModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.bone2 = root.getChild("bone2");
		this.bone3 = root.getChild("bone3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.0F, 0.0F));
		PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));
		PartDefinition cube2_r1 = bone2.addOrReplaceChild("cube2_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854f, 0.7854f, -0.7854f));
		PartDefinition bone3 = partdefinition.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));
		PartDefinition cube3_r1 = bone3.addOrReplaceChild("cube3_r1", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854f, -0.7854f, 0.7854f));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull TestEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		bone.xRot = ((ageInTicks / 20) % 180 - 90);
		bone.yRot = ((ageInTicks / 20) % 180 - 90);
		bone.zRot = -((ageInTicks / 20) % 180 - 90);
		bone2.xRot = ((ageInTicks / 20) % 180 - 90);
		bone2.yRot = -((ageInTicks / 20) % 180 - 90);
		bone2.zRot = ((ageInTicks / 20) % 180 - 90);
		bone3.xRot = -((ageInTicks / 20) % 180 - 90);
		bone3.yRot = ((ageInTicks / 20) % 180 - 90);
		bone3.zRot = ((ageInTicks / 20) % 180 - 90);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		bone.render(poseStack, buffer, packedLight, packedOverlay, color);
		bone2.render(poseStack, buffer, packedLight, packedOverlay, color);
		bone3.render(poseStack, buffer, packedLight, packedOverlay, color);
	}
}