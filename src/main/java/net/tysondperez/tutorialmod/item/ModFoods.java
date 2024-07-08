package net.tysondperez.tutorialmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(3).fast()
            .saturationModifier(0.0f).alwaysEdible().effect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SPEED, 100, 6), 1.0f).build();
}
