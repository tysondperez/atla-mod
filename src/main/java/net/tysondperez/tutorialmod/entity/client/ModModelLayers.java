package net.tysondperez.tutorialmod.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.tysondperez.tutorialmod.TutorialMod;

public class ModModelLayers {

    public static final ModelLayerLocation RHINO_LAYER = new ModelLayerLocation(
            new ResourceLocation(TutorialMod.MODID, "rhino_layer"), "main");

    public static final ModelLayerLocation SKY_BISON_LAYER = new ModelLayerLocation(
            new ResourceLocation(TutorialMod.MODID, "sky_bison_layer"), "main");

}
