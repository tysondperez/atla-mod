package net.tysondperez.mcatlamod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.tysondperez.mcatlamod.block.ModBlocks;
import net.tysondperez.mcatlamod.entity.ModEntities;
import net.tysondperez.mcatlamod.entity.client.*;
import net.tysondperez.mcatlamod.item.ModCreativeModeTabs;
import net.tysondperez.mcatlamod.item.ModItems;
import net.tysondperez.mcatlamod.loot.ModLootModifiers;
import net.tysondperez.mcatlamod.sound.ModSounds;
import net.tysondperez.mcatlamod.villagers.ModVillagers;
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
@Mod(ATLAMod.MODID)
public class ATLAMod {
    public static final String MODID = "mcatlamod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation id(String path)
    {
        return new ResourceLocation(MODID, path);
    }

    public ATLAMod() {
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
        bus.addListener((TickEvent.ClientTickEvent e) -> clientTick(e.phase == TickEvent.Phase.START));
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

        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(ModItems.SKY_BISON_SPAWN_EGG);
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.BIG_SADDLE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    public void onLoadComplete(ModConfigEvent.Loading event)
    {
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
