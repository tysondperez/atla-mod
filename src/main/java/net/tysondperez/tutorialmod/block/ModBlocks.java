package net.tysondperez.tutorialmod.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.block.custom.SoundBlock;
import net.tysondperez.tutorialmod.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TutorialMod.MODID);

    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerBlock("sapphire_block",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> RAW_SAPPHIRE_BLOCK = registerBlock("raw_sapphire_block",
            ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK)));

    public static final RegistryObject<Block> SAPPHIRE_ORE = registerBlock("sapphire_ore",
            ()-> new DropExperienceBlock(UniformInt.of(3, 6),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2f)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = registerBlock("deepslate_sapphire_ore",
            ()-> new DropExperienceBlock(UniformInt.of(3, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).strength(3f)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NETHER_SAPPHIRE_ORE = registerBlock("nether_sapphire_ore",
            ()-> new DropExperienceBlock(UniformInt.of(3, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK).strength(1f)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> END_STONE_SAPPHIRE_ORE = registerBlock("end_stone_sapphire_ore",
            ()-> new DropExperienceBlock(UniformInt.of(3, 7),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE).strength(5f)
                            .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> SOUND_BLOCK = registerBlock("sound_block",
            ()-> new SoundBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NOTE_BLOCK)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
