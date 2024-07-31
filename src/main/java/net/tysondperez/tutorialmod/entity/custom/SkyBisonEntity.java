package net.tysondperez.tutorialmod.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.tysondperez.tutorialmod.entity.ModEntities;
import net.tysondperez.tutorialmod.entity.ai.RhinoAttackGoal;
import net.tysondperez.tutorialmod.entity.ai.SkyBisonBreedGoal;
import org.jetbrains.annotations.Nullable;

public class SkyBisonEntity extends TamableAnimal implements Saddleable, FlyingAnimal, PlayerRideable {

    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.BOOLEAN);

    private boolean flying;

    public SkyBisonEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

//        if(this.isAttacking() && attackAnimationTimeout <= 0) {
//            attackAnimationTimeout = 80; // Length in ticks of your animation
//            attackAnimationState.start(this.tickCount);
//        } else {
//            --this.attackAnimationTimeout;
//        }

//        if(!this.isAttacking()) {
//            attackAnimationState.stop();
//        }
    }




    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        //this.goalSelector.addGoal(1, new RhinoAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new SkyBisonBreedGoal(this));
        //this.goalSelector.addGoal(2, new SkyBisonTemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_BEEF), false, 50));

        this.goalSelector.addGoal(2, new FollowParentGoal(this, 1.1D));

        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6f));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 2f);
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.SKY_BISON.get().create(serverLevel);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.COOKED_BEEF);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.HOGLIN_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.RAVAGER_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DOLPHIN_DEATH;
    }

    @Override
    public boolean isSaddleable() {
        return isAlive() && isAdult() && isTame();
    }

    public boolean isAdult() {
        return true;
    }

    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        setSaddled(true);
        level().playSound(null, getX(), getY(), getZ(), SoundEvents.HORSE_SADDLE,
                soundSource, 1f, 1f);
    }

    private void setSaddled(boolean saddled)
    {
        entityData.set(SADDLED, saddled);
    }

    @Override
    public boolean isSaddled() {
        return entityData.get(SADDLED);
    }

    @Override
    public boolean isFlying() {
        return flying;
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.isFlying()) {
            f = 0.0f; // No walking animation while flying
        } else if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }
}
