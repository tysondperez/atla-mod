package net.tysondperez.mcatlamod.entity.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;

import net.tysondperez.mcatlamod.ATLAMod;
import net.tysondperez.mcatlamod.AppaConfig;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;
import org.lwjgl.glfw.GLFW;

public class KeyMappings {
    public static final KeyMapping FLIGHT_DESCENT_KEY = keymap("flight_descent", GLFW.GLFW_KEY_Z, "key.categories.movement");
    public static final KeyMapping CAMERA_CONTROLS = keymap("camera_flight", GLFW.GLFW_KEY_F6, "key.categories.movement");

    @SuppressWarnings({"ConstantConditions"})
    private static KeyMapping keymap(String name, int defaultMapping, String category)
    {
        return new KeyMapping(String.format("key.%s.%s", ATLAMod.MODID, name), defaultMapping, category);
    }

    public static void registerKeybinds(Consumer<KeyMapping> registrar)
    {
        registrar.accept(FLIGHT_DESCENT_KEY);
        registrar.accept(CAMERA_CONTROLS);
    }

    public static void handleKeyPress(int key, int action)
    {
        if (key == CAMERA_CONTROLS.getKey().getValue()
                && action == GLFW.GLFW_PRESS
                && Minecraft.getInstance().player.getVehicle() instanceof SkyBisonEntity d)
        {
            AppaConfig.CAMERA_DRIVEN_FLIGHT.set(!AppaConfig.cameraDrivenFlight());
            ATLAMod.LOGGER.info("camera flight set to "+ AppaConfig.CAMERA_DRIVEN_FLIGHT.get());
            Minecraft.getInstance().player.displayClientMessage(Component.translatable("mount.sky_bison.camera_controls." + (AppaConfig.cameraDrivenFlight()? "enabled" : "disabled"), d.getDisplayName()), true);
        }
    }
}
