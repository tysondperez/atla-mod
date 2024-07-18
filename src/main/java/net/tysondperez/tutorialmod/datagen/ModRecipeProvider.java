package net.tysondperez.tutorialmod.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.block.ModBlocks;
import net.tysondperez.tutorialmod.item.ModItems;
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
    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPPHIRE.get(),
            ModBlocks.SAPPHIRE_ORE.get(), ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModBlocks.NETHER_SAPPHIRE_ORE.get(),
            ModBlocks.END_STONE_SAPPHIRE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f,
                200, "sapphire");
        oreBlasting(pWriter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f,
                100, "sapphire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))
                .save(pWriter, new ResourceLocation("myuniq3131id", "sapphire_block_from_sapphire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get() /*result of the recipe*/) // Shaped Recipe
                .pattern("FFF") // {
                .pattern("FFF") //   pattern of recipe
                .pattern("FFF") // }
                .define('F', Items.STICK) // keys
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK)) // what item unlocks this recipe in the recipe book
                .save(pWriter, new ResourceLocation("myuniq3131id", "sapphire_block_from_sticks"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9)
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
                                      RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme,
                                      String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
                                      RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime,
                                      String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<?
            extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory,
                                     ItemLike pResult, float pExperience, int pCookingTime, String pGroup,
                                     String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  TutorialMod.MODID + ":" + getItemName(pResult) +
                            pRecipeName + "_" + getItemName(itemlike));
        }
    }
}