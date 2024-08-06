package net.tysondperez.mcatlamod.entity.client;// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.tysondperez.mcatlamod.entity.animations.ModAnimationDefinitions;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

public class SkyBisonModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "sky_bison"), "main");
	private final ModelPart sky_bison;
	private final ModelPart head;
	

	public SkyBisonModel(ModelPart root) {
		this.sky_bison = root.getChild("sky_bison");
		this.head = sky_bison.getChild("body").getChild("torso").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition sky_bison = partdefinition.addOrReplaceChild("sky_bison", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 30.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition body = sky_bison.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -88.0F, 16.0F, 48.0F, 56.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(0, 94).addBox(-24.0F, -82.0F, -16.0F, 48.0F, 52.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(0, 184).addBox(-24.0F, -80.0F, -48.0F, 48.0F, 48.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create().texOffs(6, 268).addBox(-25.375F, -20.75F, -15.25F, 51.0F, 18.0F, 32.0F, new CubeDeformation(0.0F))
				.texOffs(6, 411).addBox(-24.375F, -2.75F, -15.25F, 49.0F, 18.0F, 31.0F, new CubeDeformation(0.0F))
				.texOffs(25, 339).addBox(-22.375F, -18.75F, -30.25F, 44.0F, 33.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(198, 138).addBox(-13.375F, 4.25F, 15.75F, 27.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.375F, -54.25F, 67.25F, 0.0F, 0.1745F, 0.0F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(229, 72).addBox(2.0F, -1.0F, -9.0F, 5.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.375F, -11.75F, -5.25F, 0.0F, 3.1416F, 0.0F));

		PartDefinition cube_r1 = left_horn.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(290, 49).addBox(3.0F, -21.0F, -7.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition cube_r2 = left_horn.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(302, 72).addBox(3.0F, -21.0F, -7.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 9.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cube_r3 = left_horn.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(205, 78).addBox(0.7025F, -20.068F, -6.9922F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 12.0F, -1.0F, 0.0113F, -0.0496F, 0.5567F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(244, 44).addBox(4.0F, -1.0F, 3.0F, 5.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(21.625F, -11.75F, -5.25F));

		PartDefinition cube_r4 = right_horn.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(244, 99).addBox(3.0F, -21.0F, -1.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 6.0F, 0.0F, 0.0F, 0.48F));

		PartDefinition cube_r5 = right_horn.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(293, 99).addBox(3.0F, -21.0F, -1.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 9.0F, 6.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition cube_r6 = right_horn.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(207, 50).addBox(1.0F, -20.0F, -1.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 12.0F, 5.0F, 0.0113F, -0.0496F, 0.5567F));

		PartDefinition left_eye = head.addOrReplaceChild("left_eye", CubeListBuilder.create().texOffs(275, 76).addBox(-1.1F, -0.1F, -4.2F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(280, 70).addBox(0.0F, 1.0F, -3.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.575F, -0.65F, 16.85F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_eye = head.addOrReplaceChild("right_eye", CubeListBuilder.create().texOffs(275, 76).addBox(-1.1F, -0.1F, -4.2F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(280, 70).addBox(0.0F, 1.2F, -2.8F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.425F, -0.65F, 16.85F, 0.0F, -1.5708F, 0.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition front = tail.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r7 = front.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(307, 93).addBox(-71.0F, -6.0F, -31.0F, 48.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(47.0F, -68.0F, -48.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition mid = tail.addOrReplaceChild("mid", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r8 = mid.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(307, 93).addBox(-71.0F, -6.0F, -31.0F, 48.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(47.0F, -61.0F, -79.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition back = tail.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r9 = back.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(307, 93).addBox(-71.0F, -6.0F, -31.0F, 48.0F, 16.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(47.0F, -54.0F, -110.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_legs = body.addOrReplaceChild("left_legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_front_leg = left_legs.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition lftop = left_front_leg.addOrReplaceChild("lftop", CubeListBuilder.create().texOffs(272, 184).addBox(-36.0F, -32.0F, 7.0F, 16.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -16.0F, 15.0F));

		PartDefinition lfknee = left_front_leg.addOrReplaceChild("lfknee", CubeListBuilder.create().texOffs(278, 236).addBox(-33.0F, -56.0F, 6.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(282, 152).addBox(-33.0F, -33.0F, 6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 23.0F, 17.0F));

		PartDefinition left_middle_leg = left_legs.addOrReplaceChild("left_middle_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -30.0F));

		PartDefinition lmtop = left_middle_leg.addOrReplaceChild("lmtop", CubeListBuilder.create().texOffs(272, 184).addBox(-36.0F, -32.0F, 7.0F, 16.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -16.0F, 15.0F));

		PartDefinition lmknee = left_middle_leg.addOrReplaceChild("lmknee", CubeListBuilder.create().texOffs(278, 236).addBox(-33.0F, -56.0F, 6.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(282, 152).addBox(-33.0F, -33.0F, 6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 23.0F, 17.0F));

		PartDefinition left_back_leg = left_legs.addOrReplaceChild("left_back_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -62.0F));

		PartDefinition lbtop = left_back_leg.addOrReplaceChild("lbtop", CubeListBuilder.create().texOffs(272, 184).addBox(-36.0F, -32.0F, 7.0F, 16.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -16.0F, 15.0F));

		PartDefinition lbknee = left_back_leg.addOrReplaceChild("lbknee", CubeListBuilder.create().texOffs(278, 236).addBox(-33.0F, -56.0F, 6.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(282, 152).addBox(-33.0F, -33.0F, 6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 23.0F, 17.0F));

		PartDefinition right_legs = body.addOrReplaceChild("right_legs", CubeListBuilder.create(), PartPose.offset(36.0F, 0.0F, 0.0F));

		PartDefinition right_front_leg = right_legs.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition rftop = right_front_leg.addOrReplaceChild("rftop", CubeListBuilder.create().texOffs(272, 184).mirror().addBox(-36.0F, -32.0F, 7.0F, 16.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, -16.0F, 15.0F));

		PartDefinition rfknee = right_front_leg.addOrReplaceChild("rfknee", CubeListBuilder.create().texOffs(278, 236).mirror().addBox(-33.0F, -56.0F, 6.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(282, 152).mirror().addBox(-33.0F, -33.0F, 6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 23.0F, 17.0F));

		PartDefinition right_middle_leg = right_legs.addOrReplaceChild("right_middle_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -30.0F));

		PartDefinition rmtop = right_middle_leg.addOrReplaceChild("rmtop", CubeListBuilder.create().texOffs(272, 184).mirror().addBox(-36.0F, -32.0F, 7.0F, 16.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, -16.0F, 15.0F));

		PartDefinition rmknee = right_middle_leg.addOrReplaceChild("rmknee", CubeListBuilder.create().texOffs(278, 236).mirror().addBox(-33.0F, -56.0F, 6.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(282, 152).mirror().addBox(-33.0F, -33.0F, 6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 23.0F, 17.0F));

		PartDefinition right_back_leg = right_legs.addOrReplaceChild("right_back_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -62.0F));

		PartDefinition rbtop = right_back_leg.addOrReplaceChild("rbtop", CubeListBuilder.create().texOffs(272, 184).mirror().addBox(-36.0F, -32.0F, 7.0F, 16.0F, 24.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(10.0F, -16.0F, 15.0F));

		PartDefinition rbknee = right_back_leg.addOrReplaceChild("rbknee", CubeListBuilder.create().texOffs(278, 236).mirror().addBox(-33.0F, -56.0F, 6.0F, 14.0F, 23.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(282, 152).mirror().addBox(-33.0F, -33.0F, 6.0F, 14.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 23.0F, 17.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.SKY_BISON_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((SkyBisonEntity) entity).idleAnimationState, ModAnimationDefinitions.SKY_BISON_IDLE, ageInTicks, 1f);
		this.animate(((SkyBisonEntity) entity).flyAnimationState, ModAnimationDefinitions.SKY_BISON_FLY, ageInTicks, 1f);
		//this.animate(((SkyBisonEntity) entity).attackAnimationState, ModAnimationDefinitions.SKY_BISON_ATTACK, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -10.0F, 10.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 10.0F);

		this.head.yRot = pNetHeadYaw * -((float)Math.PI / 180F);
		this.head.xRot = pHeadPitch * -((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		sky_bison.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return sky_bison;
	}
}