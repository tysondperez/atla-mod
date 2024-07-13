package net.tysondperez.tutorialmod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SapphireStaffItem extends Item {
    public SapphireStaffItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer,
                                                           @NotNull InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide()){
            LargeFireball fireball = new LargeFireball(EntityType.FIREBALL, pLevel);

            Vec3 look = pPlayer.getLookAngle();

            // Calculate the spawn position for the fire charge
            double x = pPlayer.getX() + look.x * 2;
            double y = pPlayer.getEyeY() + look.y * 2;
            double z = pPlayer.getZ() + look.z * 2;

            fireball.moveTo(x, y, z, pPlayer.getYRot(), pPlayer.getXRot());
            fireball.setDeltaMovement(look.x * 4, look.y * 4, look.z * 4);
            fireball.setOwner(pPlayer);

            pLevel.addFreshEntity(fireball);

            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FIRECHARGE_USE,
                    SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        itemStack.hurtAndBreak(1, pPlayer,
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResultHolder.success(itemStack);
    }
}
