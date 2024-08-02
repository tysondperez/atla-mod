package net.tysondperez.tutorialmod.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SaddleItem;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.entity.custom.SkyBisonEntity;

public class BigSaddleItem extends SaddleItem {
    public BigSaddleItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pTarget, InteractionHand pHand) {
        if (!(pTarget instanceof SkyBisonEntity)){
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(pStack, pPlayer, pTarget, pHand);
    }
}
