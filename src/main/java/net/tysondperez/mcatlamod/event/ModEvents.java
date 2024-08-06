package net.tysondperez.mcatlamod.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.block.ModBlocks;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;
import net.tysondperez.mcatlamod.item.ModItems;
import net.tysondperez.mcatlamod.item.custom.BigSaddleItem;
import net.tysondperez.mcatlamod.villagers.ModVillagers;

import java.util.List;

@Mod.EventBusSubscriber(modid = ATLAMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof SkyBisonEntity) {
            ItemStack stack = event.getItemStack();
            // Handle saddle
            if (stack.getItem() instanceof SaddleItem) {
                event.setCancellationResult(InteractionResult.FAIL);
                event.setCanceled(true);
            }
            if (stack.getItem() instanceof BigSaddleItem && !((SkyBisonEntity) (event.getTarget())).isSaddled()) {
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(false);
            }
        }
    }
}
