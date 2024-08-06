package net.tysondperez.mcatlamod.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

public class SkyBisonBodyController extends BodyRotationControl {

    private final SkyBisonEntity bison;

    public SkyBisonBodyController(SkyBisonEntity pMob) {
        super(pMob);
        this.bison = pMob;
    }

    @Override
    public void clientTick() {
        bison.yBodyRot = bison.getYRot();

        bison.yHeadRot = Mth.rotateIfNecessary(bison.yHeadRot, bison.yBodyRot, bison.getMaxHeadYRot());
    }
}
