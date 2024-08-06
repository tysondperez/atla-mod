package net.tysondperez.mcatlamod.item;


import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.block.ModBlocks;
import net.tysondperez.mcatlamod.entity.ModEntities;
import net.tysondperez.mcatlamod.item.custom.*;
import net.tysondperez.mcatlamod.sound.ModSounds;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ATLAMod.MODID);

    public static final RegistryObject<Item> BIG_SADDLE = ITEMS.register("big_saddle",
            () -> new BigSaddleItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SKY_BISON_SPAWN_EGG = ITEMS.register("sky_bison_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.SKY_BISON, 0xFEF7DB, 0x936F50,
                    new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
