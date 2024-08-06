package net.tysondperez.mcatlamod.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.block.ModBlocks;
import net.tysondperez.mcatlamod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BIG_SADDLE.get())
                .pattern("SS")
                .pattern("SS")
                .define('S', Items.SADDLE)
                .unlockedBy(getHasName(Items.SADDLE), has(Items.SADDLE))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SADDLE, 4)
                .requires(ModItems.BIG_SADDLE.get())
                .unlockedBy(getHasName(ModItems.BIG_SADDLE.get()), has(ModItems.BIG_SADDLE.get()))
                .save(pWriter);
    }
}