package net.tysondperez.mcatlamod.entity.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

public class MountControlsMessenger
{
    private static int delay = 0;

    public static void sendControlsMessage()
    {
        // the length the initial "dismount" message is displayed for, in ticks.
        // Our message displays after 60 ticks (after the dismount message.)
        // taken from Gui#setOverlayMessage.
        delay = 60;
    }

    @SuppressWarnings("ConstantConditions")
    public static void tick()
    {
        if (delay > 0)
        {
            var player = Minecraft.getInstance().player;
            if (!(player.getVehicle() instanceof SkyBisonEntity))
            {
                delay = 0;
                return;
            }

            --delay;

            if (delay == 0)
                player.displayClientMessage(Component.translatable("mount.sky_bison.vertical_controls",
                        Minecraft.getInstance().options.keyJump.getTranslatedKeyMessage(),
                        KeyMappings.FLIGHT_DESCENT_KEY.getTranslatedKeyMessage()), true);
        }
    }
}
