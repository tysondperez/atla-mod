package net.tysondperez.mcatlamod.datagen;

import net.tysondperez.mcatlamod.ATLAMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, ATLAMod.MODID);
    }

    @Override
    protected void start() {

    }
}