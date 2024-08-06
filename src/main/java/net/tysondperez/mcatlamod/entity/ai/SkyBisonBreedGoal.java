package net.tysondperez.mcatlamod.entity.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.tysondperez.mcatlamod.entity.custom.SkyBisonEntity;

import java.util.List;

public class SkyBisonBreedGoal extends BreedGoal {

    private final SkyBisonEntity bison;

    public SkyBisonBreedGoal(SkyBisonEntity animal)
    {
        super(animal, 1);
        this.bison = animal;
    }

    @Override
    public boolean canUse()
    {
        if (!bison.isAdult()) return false;
        if (!bison.isInLove()) return false;
        else return (partner = getNearbyMate()) != null;
    }

    public SkyBisonEntity getNearbyMate()
    {
        List<SkyBisonEntity> list = level.getEntitiesOfClass(SkyBisonEntity.class, bison.getBoundingBox().inflate(8d));
        double dist = Double.MAX_VALUE;
        SkyBisonEntity closest = null;

        for (SkyBisonEntity entity : list)
        {
            if (bison.canMate(entity) && bison.distanceToSqr(entity) < dist)
            {
                closest = entity;
                dist = bison.distanceToSqr(entity);
            }
        }

        return closest;
    }

    @Override
    protected void breed()
    {
        // Respect Mod compatibility
        if (MinecraftForge.EVENT_BUS.post(new BabyEntitySpawnEvent(animal, partner, null)))
        {
            // Reset the "inLove" state for the animals
            animal.setAge(6000);
            partner.setAge(6000);
            return;
        }

        animal.resetLove();
        partner.resetLove();
        bison.spawnChildFromBreeding((ServerLevel) level, partner);
        level.broadcastEntityEvent(this.animal, (byte) 18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT))
            level.addFreshEntity(new ExperienceOrb(level, animal.getX(), animal.getY(), animal.getZ(), animal.getRandom().nextInt(7) + 1));
    }

}
