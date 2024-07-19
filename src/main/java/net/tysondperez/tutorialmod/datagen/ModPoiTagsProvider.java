package net.tysondperez.tutorialmod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tysondperez.tutorialmod.TutorialMod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModPoiTagsProvider extends PoiTypeTagsProvider {
    public ModPoiTagsProvider(PackOutput p_256012_, CompletableFuture<HolderLookup.Provider> p_256617_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256012_, p_256617_, TutorialMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptional(new ResourceLocation(TutorialMod.MODID, "sound_poi"));
    }
}
