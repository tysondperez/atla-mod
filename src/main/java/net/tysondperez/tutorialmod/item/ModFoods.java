package net.tysondperez.tutorialmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2).fast()
            .saturationMod(0.2f).alwaysEat().effect(() ->
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 2), 1.0f).build();

    public static final FoodProperties CORN = new FoodProperties.Builder().nutrition(2).fast()
            .saturationMod(0.2f).alwaysEat().effect(() ->
                    new MobEffectInstance(MobEffects.JUMP, 200, 2), 1.0f).build();
}
