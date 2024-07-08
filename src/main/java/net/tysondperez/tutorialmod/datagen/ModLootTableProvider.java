package net.tysondperez.tutorialmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.tysondperez.tutorialmod.datagen.loot.ModBlockLootTables;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider {

    public  static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> future){
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)
        ), future);
    }

    //generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput, lookupProvider));
}
