package net.tysondperez.tutorialmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.entity.custom.SkyBisonEntity;
import net.tysondperez.tutorialmod.entity.custom.SkyBisonEntity;

public class SkyBisonRenderer extends MobRenderer<SkyBisonEntity, SkyBisonModel<SkyBisonEntity>>{
    public SkyBisonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SkyBisonModel<>(pContext.bakeLayer(ModModelLayers.SKY_BISON_LAYER)), 2f);
    }
    
    @Override
    public ResourceLocation getTextureLocation(SkyBisonEntity pEntity) {
        return new ResourceLocation(TutorialMod.MODID, "textures/entity/sky_bison.png");
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
