package net.tysondperez.tutorialmod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.tysondperez.tutorialmod.entity.client.MountCameraManager;

import static net.tysondperez.tutorialmod.TutorialMod.*;

//@Mod(TutorialMod.MODID)
//public class ForgeModImpl
//{
//    public static final SimpleChannel NETWORK;
//
//    public ForgeModImpl()
//    {
//        var bus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, DMLConfig.CLIENT_SPEC);
//
//        setupEvents();
//    }
//
//    static
//    {
//        var PROTOCOL_VERSION = "1.O";
//        NETWORK = NetworkRegistry.ChannelBuilder.named(TutorialMod.id("network"))
//                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
//                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
//                .networkProtocolVersion(() -> PROTOCOL_VERSION)
//                .simpleChannel();
//    }
//
//    private static void setupEvents()
//    {
//        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
//        var bus = MinecraftForge.EVENT_BUS;
//
//        //bus.addListener((AddReloadListenerEvent e) -> registerReloadListeners(e::addListener));
//
//        //modBus.addListener((EntityAttributeCreationEvent e) -> registerEntityAttributes(e::put));
//        //modBus.addListener((DataPackRegistryEvent.NewRegistry e) -> registerDatapacks(e::dataPackRegistry));
//
//        if (FMLLoader.getDist() == Dist.CLIENT) // Client Events
//        {
//            bus.addListener((TickEvent.ClientTickEvent e) -> clientTick(e.phase == TickEvent.Phase.START));
//            bus.addListener((ViewportEvent.ComputeCameraAngles e) -> MountCameraManager.setMountCameraAngles(e.getCamera()));
//            bus.addListener((InputEvent.Key e) -> onKeyPress(e.getKey(), e.getAction(), e.getModifiers()));
//
//            //modBus.addListener((ModelEvent.RegisterGeometryLoaders e) -> registerEggModelLoader(e::register));
//            //modBus.addListener((BuildCreativeModeTabContentsEvent e) -> registerCreativeTabItems(e.getTabKey(), e::accept));
//            //modBus.addListener((EntityRenderersEvent.RegisterRenderers e) -> registerRenderers());
//            //modBus.addListener((RegisterColorHandlersEvent.Item e) -> registerItemColors(e.getItemColors()));
//            //modBus.addListener((FMLConstructModEvent e) -> e.enqueueWork(DragonMountsLegacy::registerReloadListenersEarly));
//            modBus.addListener((RegisterKeyMappingsEvent e) -> registerKeyBindings(e::register));
//        }
//    }
//}
