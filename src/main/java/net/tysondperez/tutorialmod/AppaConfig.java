package net.tysondperez.tutorialmod;

import net.minecraftforge.common.ForgeConfigSpec;

public class AppaConfig
{

    public static final ForgeConfigSpec CLIENT_SPEC;

    public static final ForgeConfigSpec.BooleanValue CAMERA_DRIVEN_FLIGHT;

    public static boolean cameraDrivenFlight()
    {
        return CAMERA_DRIVEN_FLIGHT.get();
    }

    public static final ForgeConfigSpec.BooleanValue THIRD_PERSON_ON_MOUNT;

    public static boolean thirdPersonOnMount()
    {
        return THIRD_PERSON_ON_MOUNT.get();
    }

    // [0][0..2] = Back third person  ; distance, vertical, horizontal
    // [1][0..2] = Front third person ; distance, vertical, horizontal
    private static final ForgeConfigSpec.DoubleValue[][] CAMERA_OFFSETS = new ForgeConfigSpec.DoubleValue[2][3];

    public static ForgeConfigSpec.DoubleValue[] getCameraPerspectiveOffset(boolean back)
    {
        //back = false;
        return CAMERA_OFFSETS[back? 0 : 1];
    }

    static
    {
        var configurator = new ForgeConfigSpec.Builder()
                .push("client");

        CAMERA_DRIVEN_FLIGHT = configurator.comment(
                        "Is dragon flight vertical movement driven by the pitch of the game camera?",
                        "This option can be toggled in-game via keybinding for quick switching.",
                        "If you choose to disable this, vertical movement can still be achieved via dedicated keybindings.")
                .define("camera_driven_flight", true);

        THIRD_PERSON_ON_MOUNT = configurator.comment(
                        "When mounting and dismounting a dragon, should the camera automatically switch third-person perspectives?")
                .define("third_person_on_mount", true);

        configurator.pop();

        configurator.comment(
                        "The values that define the offset of the camera position when riding a dragon in the third person camera.")
                .push("camera_offsets");
        defineCameraOffsetEntries(configurator);
        configurator.pop();

        CLIENT_SPEC = configurator.build();
    }

    private static void defineCameraOffsetEntries(ForgeConfigSpec.Builder configurator)
    {
        TutorialMod.LOGGER.info("building arrays!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        var perspectiveName = "back";
        for (ForgeConfigSpec.DoubleValue[] perspective : CAMERA_OFFSETS)
        {
            configurator.push("third_person_" + perspectiveName);

            if (perspectiveName.equals("back")){
                perspective[0] = configurator.defineInRange("distance", 6.0, -3, 1000);
            } else {
                perspective[0] = configurator.defineInRange("distance", 8.0, -3, 1000);
            }
            perspectiveName = "front";


            perspective[1] = configurator.defineInRange("vertical", 0.0, -3, 1000);
            perspective[2] = configurator.defineInRange("horizontal", 0.0, -1000, 1000);



            configurator.pop();
        }
    }

    public static void printOffsets(){
        TutorialMod.LOGGER.info("arrays built");
        for (int i = 0; i < 3; i++){
            TutorialMod.LOGGER.info("value at arr["+i+"]: "+CAMERA_OFFSETS[0][i].get());
        }
        for (int i = 0; i < 3; i++){
            TutorialMod.LOGGER.info("value at arr["+i+"]: "+CAMERA_OFFSETS[1][i].get());
        }
    }
}
