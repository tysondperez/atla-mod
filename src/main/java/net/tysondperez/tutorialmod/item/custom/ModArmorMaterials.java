package net.tysondperez.tutorialmod.item.custom;

/*
    Note: As of 1.20.6, changes to the ArmorMaterials class make this approach infeasible.
    This class is included for reference only.
 */



import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.item.ModItems;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMORS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, TutorialMod.MODID);

    public static final RegistryObject<ArmorMaterial> SAPPHIRE = registerArmor("sapphire",
            Util.make(new EnumMap<>(ArmorItem.Type.class), enumMap -> {
                enumMap.put(ArmorItem.Type.BOOTS, 1);
                enumMap.put(ArmorItem.Type.LEGGINGS, 4);
                enumMap.put(ArmorItem.Type.CHESTPLATE, 5);
                enumMap.put(ArmorItem.Type.HELMET, 2);
                enumMap.put(ArmorItem.Type.BODY, 4);
            }),
            25, SoundEvents.ARMOR_EQUIP_GOLD, 1f, 0.0F,
            () -> Ingredient.of(ModItems.SAPPHIRE.get()));

    private static RegistryObject<ArmorMaterial> registerArmor(String pGroup, EnumMap<ArmorItem.Type, Integer> pEnumMap,
                                                               int pEnchantmentValue, Holder<SoundEvent> pEquipSound,
                                                               float pToughness, float pKnockbackResistance,
                                                               Supplier<Ingredient> pRepairIngredient) {
        List<ArmorMaterial.Layer> pLayerList = List.of(new ArmorMaterial.Layer(new ResourceLocation(pGroup)));
        return registerArmor(pGroup, pEnumMap, pEnchantmentValue, pEquipSound, pToughness, pKnockbackResistance,
                pRepairIngredient, pLayerList);
    }

    private static RegistryObject<ArmorMaterial> registerArmor( String pGroup, EnumMap<ArmorItem.Type, Integer> pEnumMap,
                                                                int pEnchantmentValue, Holder<SoundEvent> pEquipSound, float pToughness,
                                                                float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient,
                                                                List<ArmorMaterial.Layer> pLayerList) {

        return ARMORS.register(pGroup,
                () -> new ArmorMaterial(pEnumMap, pEnchantmentValue, pEquipSound, pRepairIngredient,
                        pLayerList, pToughness, pKnockbackResistance));

    }

    public static void register(IEventBus eventBus) {
        ARMORS.register(eventBus);
    }


    public static final Map<ResourceLocation, ArmorMaterial> MATERIALS = new HashMap<>();

    public static void registerArmorMaterials() {
        MATERIALS.put(new ResourceLocation(TutorialMod.MODID, "sapphire_armor"), SAPPHIRE.get());
    }
}
