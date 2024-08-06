package net.tysondperez.mcatlamod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

public class SkyBisonRenderer extends MobRenderer<SkyBisonEntity, SkyBisonModel<SkyBisonEntity>>{

    private static final ResourceLocation SADDLE_TEXTURE =
            new ResourceLocation("mcatlamod", "textures/entity/saddle.png");

    public SkyBisonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SkyBisonModel<>(pContext.bakeLayer(ModModelLayers.SKY_BISON_LAYER)), 2f);

        this.addLayer(new RenderLayer<>(this) {
            @Override
            public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, SkyBisonEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
                if (entity.isSaddled()) {
                    VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(SADDLE_TEXTURE));
                    SkyBisonModel<SkyBisonEntity> model = SkyBisonRenderer.this.getModel();
                    model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
                    model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                    model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }
        });
    }
    
    @Override
    public ResourceLocation getTextureLocation(SkyBisonEntity pEntity) {
        return new ResourceLocation(ATLAMod.MODID, "textures/entity/sky_bison.png");
    }

    @Override
    public void render(SkyBisonEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.5f, 0.5f, 0.5f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
