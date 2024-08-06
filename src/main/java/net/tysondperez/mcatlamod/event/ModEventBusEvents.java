package net.tysondperez.mcatlamod.event;


import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.entity.ModEntities;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

@Mod.EventBusSubscriber(modid = ATLAMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SKY_BISON.get(), SkyBisonEntity.createAttributes().build());
    }
}
