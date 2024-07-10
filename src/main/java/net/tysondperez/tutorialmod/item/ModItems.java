package net.tysondperez.tutorialmod.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.item.custom.FuelItem;
import net.tysondperez.tutorialmod.item.custom.MetalDetectorItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TutorialMod.MODID);

    public static final RegistryObject<Item> SAPPHIRE = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_SAPPHIRE = ITEMS.register("raw_sapphire",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(100)));

    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new Item(new Item.Properties().food(ModFoods.STRAWBERRY)));

    public static final RegistryObject<Item> SAPPHIRE_STAFF = ITEMS.register("sapphire_staff",
            () -> new Item(new Item.Properties().stacksTo(1)));



    public static final RegistryObject<Item> SAPPHIRE_SWORD = ITEMS.register("sapphire_sword",
            () -> new SwordItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.DIAMOND, 3, -1.6F)))
    );
    public static final RegistryObject<Item>SAPPHIRE_SHOVEL = ITEMS.register("sapphire_shovel", 
            () -> new ShovelItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(Tiers.DIAMOND, 1.5F, -3.0F)))
    );
    public static final RegistryObject<Item>SAPPHIRE_PICKAXE = ITEMS.register("sapphire_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 1.0F, -2.8F)))
    );
    public static final RegistryObject<Item>SAPPHIRE_AXE = ITEMS.register("sapphire_axe",
            () -> new AxeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(AxeItem.createAttributes(Tiers.DIAMOND, 5.0F, -2.6F)))
    );
    public static final RegistryObject<Item>SAPPHIRE_HOE = ITEMS.register("sapphire_hoe",
            () -> new HoeItem(Tiers.DIAMOND, new Item.Properties()
                    .attributes(HoeItem.createAttributes(Tiers.DIAMOND, -3.0F, 0.0F)))
    );

    public static final RegistryObject<Item> PINE_CONE = ITEMS.register("pine_cone",
            () -> new FuelItem(new Item.Properties(), 400));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
