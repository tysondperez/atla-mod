package net.tysondperez.tutorialmod.entity.client;

import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.tysondperez.tutorialmod.DMLConfig;
import net.tysondperez.tutorialmod.entity.custom.SkyBisonEntity;

public class MountCameraManager
{
    private static CameraType previousPerspective = CameraType.FIRST_PERSON;

    public static void onBisonMount()
    {
        boolean truth = true;
        //truth = DMLConfig.thirdPersonOnMount()
        if (truth)
        {
            previousPerspective = Minecraft.getInstance().options.getCameraType();
            Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
        }
    }

    public static void onBisonDismount()
    {
        boolean truth = true;
        //truth = DMLConfig.thirdPersonOnMount()
        if (truth)
            Minecraft.getInstance().options.setCameraType(previousPerspective);
    }

    @SuppressWarnings("ConstantConditions") // player should never be null at time of calling
    public static void setMountCameraAngles(Camera camera)
    {
        if (Minecraft.getInstance().player.getVehicle() instanceof SkyBisonEntity && !Minecraft.getInstance().options.getCameraType().isFirstPerson())
        {
            var offsets = DMLConfig.getCameraPerspectiveOffset(Minecraft.getInstance().options.getCameraType() == CameraType.THIRD_PERSON_BACK);
            camera.move(0, offsets[1].get(), offsets[2].get());
            camera.move(-camera.getMaxZoom(offsets[0].get()), 0, 0); // do distance calcs AFTER our new position is set
        }
    }
}
