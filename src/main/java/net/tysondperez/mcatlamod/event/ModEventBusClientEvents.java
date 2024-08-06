package net.tysondperez.mcatlamod.event;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.entity.client.ModModelLayers;
import net.tysondperez.mcatlamod.entity.client.SkyBisonModel;

@Mod.EventBusSubscriber(modid = ATLAMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.SKY_BISON_LAYER, SkyBisonModel::createBodyLayer);
    }
}
