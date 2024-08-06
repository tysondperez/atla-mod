package net.tysondperez.mcatlamod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ATLAMod.MODID);

    public static final RegistryObject<EntityType<SkyBisonEntity>> SKY_BISON =
            ENTITY_TYPES.register("sky_bison", () -> EntityType.Builder.of(SkyBisonEntity::new, MobCategory.CREATURE)
                    .sized(3f, 5f).build("sky_bison"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
