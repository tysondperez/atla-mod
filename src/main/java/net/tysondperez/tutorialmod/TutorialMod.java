package net.tysondperez.tutorialmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.tysondperez.tutorialmod.block.ModBlocks;
import net.tysondperez.tutorialmod.entity.ModEntities;
import net.tysondperez.tutorialmod.entity.client.*;
import net.tysondperez.tutorialmod.item.ModCreativeModeTabs;
import net.tysondperez.tutorialmod.item.ModItems;
import net.tysondperez.tutorialmod.loot.ModLootModifiers;
import net.tysondperez.tutorialmod.sound.ModSounds;
import net.tysondperez.tutorialmod.villagers.ModVillagers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.function.Consumer;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TutorialMod.MODID)
public class TutorialMod {
    public static final String MODID = "myuniq3131id";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String path)
    {
        return new ResourceLocation(MODID, path);
    }

    public TutorialMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, AppaConfig.CLIENT_SPEC);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);

        var bus = MinecraftForge.EVENT_BUS;
        bus.addListener((ViewportEvent.ComputeCameraAngles e) -> MountCameraManager.setMountCameraAngles(e.getCamera()));
        bus.addListener((InputEvent.Key e) -> onKeyPress(e.getKey(), e.getAction(), e.getModifiers()));

        modEventBus.addListener((RegisterKeyMappingsEvent e) -> registerKeyBindings(e::register));

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onLoadComplete);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            // Add strawberry seeds to the composter with a 30% chance
            ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY_SEEDS.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModItems.STRAWBERRY.get(), 0.65f);

            ComposterBlock.COMPOSTABLES.put(ModItems.CORN_SEEDS.get(), 0.3f);
            ComposterBlock.COMPOSTABLES.put(ModItems.CORN.get(), 0.65f);

            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.RAW_SAPPHIRE);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.SAPPHIRE_SHOVEL);
            event.accept(ModItems.SAPPHIRE_PICKAXE);
            event.accept(ModItems.SAPPHIRE_HOE);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.SAPPHIRE_SWORD);
            event.accept(ModItems.SAPPHIRE_AXE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public void onLoadComplete(ModConfigEvent.Loading event)
    {
        TutorialMod.LOGGER.info("event fired");
        if (event.getConfig().getSpec() == AppaConfig.CLIENT_SPEC)
        {
            AppaConfig.printOffsets();
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            TutorialMod.LOGGER.info("Client setup event fired!");
            EntityRenderers.register(ModEntities.RHINO.get(), RhinoRenderer::new);
            EntityRenderers.register(ModEntities.SKY_BISON.get(), SkyBisonRenderer::new);
        }
    }

    static void registerKeyBindings(Consumer<KeyMapping> registrar)
    {
        KeyMappings.registerKeybinds(registrar);
    }

    static void clientTick(boolean head)
    {
        if (!head) MountControlsMessenger.tick();
    }

    static void onKeyPress(int key, int action, int modifiers)
    {
        KeyMappings.handleKeyPress(key, action);
    }

}
