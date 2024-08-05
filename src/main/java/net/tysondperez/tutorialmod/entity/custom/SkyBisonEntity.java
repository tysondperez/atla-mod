package net.tysondperez.tutorialmod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SaddleItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.tysondperez.tutorialmod.AppaConfig;
import net.tysondperez.tutorialmod.TutorialMod;
import net.tysondperez.tutorialmod.entity.ModEntities;
import net.tysondperez.tutorialmod.entity.ai.SkyBisonBodyController;
import net.tysondperez.tutorialmod.entity.ai.SkyBisonBreedGoal;
import net.tysondperez.tutorialmod.entity.ai.SkyBisonMoveController;
import net.tysondperez.tutorialmod.entity.client.KeyMappings;
import net.tysondperez.tutorialmod.entity.client.MountCameraManager;
import net.tysondperez.tutorialmod.entity.client.MountControlsMessenger;
import net.tysondperez.tutorialmod.item.ModItems;
import net.tysondperez.tutorialmod.item.custom.BigSaddleItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyBisonEntity extends TamableAnimal implements Saddleable, FlyingAnimal, PlayerRideable {

    private final static float BASE_MOVEMENT_SPEED = .32f;
    private final static float BASE_FLYING_SPEED = .32f;

    public static final int GROUND_CLEARENCE_THRESHOLD = 3;

    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final String NBT_SADDLED = "Saddle";
    private static final EntityDataAccessor<Integer> AGE_ID =
            SynchedEntityData.defineId(SkyBisonEntity.class, EntityDataSerializers.INT);
    private static final String NBT_AGE = "Age";

    private final GroundPathNavigation groundNavigation;
    private final FlyingPathNavigation flyingNavigation;

    private boolean flying;
    private boolean nearGround;

    public SkyBisonEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        moveControl = new SkyBisonMoveController(this);

        flyingNavigation = new FlyingPathNavigation(this, pLevel);
        groundNavigation = new GroundPathNavigation(this, pLevel);

        flyingNavigation.setCanFloat(true);
        groundNavigation.setCanFloat(true);

        navigation = groundNavigation;
        flying = false;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    public final AnimationState flyAnimationState = new AnimationState();
    public int flyAnimationTimeout = 0;

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putBoolean(NBT_SADDLED, isSaddled());
        compound.putInt(NBT_AGE, getGrowingAge());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);

        setSaddled(compound.getBoolean(NBT_SADDLED));

        // set sync age data after we read it in AgeableMob
        setGrowingAge(compound.getInt(NBT_AGE));
    }

    @Override
    public void tick() {

        super.tick();

        if (!level().isClientSide)
        {
            // heal randomly
            if (isAlive() && getRandom().nextFloat() < 0.001) heal(1f);
        }

        nearGround = onGround() || !level().noCollision(this,
                new AABB(getX(), getY(), getZ(), getX(), getY() -
                        (GROUND_CLEARENCE_THRESHOLD * getScale()), getZ()));

        // update flying state based on the distance to the ground
        boolean flying = shouldFly();
        if (flying != isFlying())
        {
            setFlying(flying);

            // update pathfinding method
            if (!level().isClientSide){
                setNavigation(flying);
                TutorialMod.LOGGER.info("Switching Navigation to: " + (flying ? "Flying" : "Ground"));
            }
        }

        if (this.onGround()){
            setFlying(false);
        }

        if (this.isFlying()){
            navigation.stop();
        }

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(SADDLED, false);
        entityData.define(AGE_ID, 0); // default to adult stage
    }

    private void setupAnimationStates() {
        if(!this.isFlying() && this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = /*this.random.nextInt(40) +*/ 800;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isFlying() && this.flyAnimationTimeout <= 0) {
            this.flyAnimationTimeout = 40;
            this.flyAnimationState.start(this.tickCount);
            this.idleAnimationState.stop();
        } else {
            --this.flyAnimationTimeout;
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
        if(!this.isFlying()) {
            flyAnimationState.stop();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide && this.isAlive() && this.isBaby()) {
            int age = this.getGrowingAge();
            if (age < 0) {
                this.setGrowingAge(age + 1); // Increment the age by 1 each tick (20 ticks = 1 second)
            }
        }
    }

    public int getGrowingAge() {
        return this.entityData.get(AGE_ID);
    }

    public void setGrowingAge(int age) {
        this.entityData.set(AGE_ID, age);
    }

    @Override
    public boolean isBaby() {
        return this.getGrowingAge() < 0;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        //this.goalSelector.addGoal(1, new RhinoAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(1, new SkyBisonBreedGoal(this));
        //this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_BEEF), false));

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
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.FLYING_SPEED, BASE_FLYING_SPEED)
                .add(Attributes.MOVEMENT_SPEED, BASE_MOVEMENT_SPEED);
    }


    @Override
    @NotNull
    public BodyRotationControl createBodyControl()
    {
        return new SkyBisonBodyController(this);
    }

    public boolean canFly()
    {
        return isAdult();
    }

    public boolean shouldFly()
    {
        if (isFlying()) return !onGround(); // more natural landings
        return canFly() && !isInWater() && !isNearGround();
    }

    /**
     * Set the flying flag of the entity.
     */
    public void setFlying(boolean flying)
    {
        this.flying = flying;
    }

    public boolean isNearGround()
    {
        return nearGround;
    }

    public void setNavigation(boolean flying)
    {
        navigation = flying ? flyingNavigation : groundNavigation;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        SkyBisonEntity baby = ModEntities.SKY_BISON.get().create(serverLevel);
        if (baby != null){
            baby.setGrowingAge(-12000);
            baby.setTame(true);
            baby.setOwnerUUID(getOwnerUUID());
        }
        return baby;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.HAY_BLOCK);
    }

    public boolean isTameFood(ItemStack pStack) {
        return pStack.is(Items.BAMBOO);
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
        return !(entityData.get(AGE_ID) < 0);
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

    public float getHealthFraction() {
        return getHealth() / getMaxHealth();
    }

    public boolean isTamedFor(Player player) {
        return isTame() && isOwnedBy(player);
    }

    @Override
    public void setTame(boolean pTamed) {
        super.setTame(pTamed);
        if (pTamed){
            setTarget(null);
        }
        if (isAdult()){
            spawnTamingParticles(pTamed);
            spawnTamingParticles(pTamed);
        }
        TutorialMod.LOGGER.info("setTame triggered: "+pTamed);
    }

    public boolean hasLocalDriver()
    {
        return getControllingPassenger() instanceof Player p && p.isLocalPlayer();
    }

    @Override
    public void travel(Vec3 vec3)
    {
        if (isFlying())
        {
            if (isControlledByLocalInstance())
            {
                // Move relative to yaw - handled in the move controller or by driver
                moveRelative(getSpeed(), vec3);
                move(MoverType.SELF, getDeltaMovement());
                if (getDeltaMovement().lengthSqr() < 0.1) // we're not actually going anywhere, bob up and down.
                    setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.03, 0));
                setDeltaMovement(getDeltaMovement().scale(0.9f)); // smoothly slow down
            }

            calculateEntityAnimation(true);
        }
        else {
            super.travel(vec3);
        }
    }

    @Override
    protected Vec3 getRiddenInput(Player driver, Vec3 move)
    {
        double moveSideways = move.x;
        double moveY = move.y;
        double moveForward = Math.min(Math.abs(driver.zza) + Math.abs(driver.xxa), 1);

        if (isFlying() && hasLocalDriver())
        {
            moveForward = moveForward > 0? moveForward : 0;
            if (driver.jumping) moveY = 1;
            else if (KeyMappings.FLIGHT_DESCENT_KEY.isDown()) moveY = -1;
            else if (moveForward > 0 && AppaConfig.cameraDrivenFlight()) moveY = -driver.getXRot() / 90; // normalize from -1 to 1
        }

        // mimic dogshit implementation of AI movement vectors
        // the way this works is that it will mimic how setSpeed in Mob works:
        // it sets the normal speed variable,
        // and then sets the walk forward variable to the same value.
        // so if speed is 0.3, walk forward will also be 0.3 instead of 1.0.
        // so when moveRelative calculates movespeed, (walkforward * speed) we get 0.15.
        // so I guess we should do it to.
        var speed = getRiddenSpeed(driver);
        return new Vec3(moveSideways * speed, moveY * speed, moveForward * speed);
    }

    @Override
    protected void tickRidden(Player driver, Vec3 move)
    {
        // rotate head to match driver.
        float yaw = driver.yHeadRot;
        if (move.z > 0) // rotate in the direction of the drivers controls
            yaw += (float) Mth.atan2(driver.zza, driver.xxa) * (180f / (float) Math.PI) - 90;
        yHeadRot = yaw;
        setXRot(driver.getXRot() * 0.68f);

        // rotate body towards the head
        setYRot(Mth.rotateIfNecessary(yHeadRot, getYRot(), 4));

        if (isControlledByLocalInstance())
        {
            if (!isFlying() && canFly() && driver.jumping) liftOff();
        }
    }

//    @Override
//    public boolean isControlledByLocalInstance() {
//        return this.getControllingPassenger() instanceof Player;
//    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction pCallback) {
        if (this.hasPassenger(passenger)) {
            double offsetX = 0.0; // Adjust these values
            double offsetY = this.getPassengersRidingOffset() + passenger.getMyRidingOffset();
            double offsetZ = 0.0; // Adjust these values

            // Calculate new position based on entity's current position and offsets
            double newPosX = this.getX() + offsetX;
            double newPosY = this.getY() + offsetY;
            double newPosZ = this.getZ() + offsetZ;

            passenger.setPos(newPosX, newPosY, newPosZ);
        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + .6f;
    }

    @Override
    public LivingEntity getControllingPassenger()
    {
        return getFirstPassenger() instanceof LivingEntity driver && isOwnedBy(driver)? driver : null;
    }

    @Override
    protected void addPassenger(Entity passenger)
    {
        super.addPassenger(passenger);

        if (passenger instanceof Player)
        {
            passenger.setYRot(getYRot());
            passenger.setXRot(getXRot());
        }

        if (hasLocalDriver())
        {
            MountControlsMessenger.sendControlsMessage();
            MountCameraManager.onBisonMount();
        }
    }

    @Override
    protected void removePassenger(Entity passenger)
    {
        if (hasLocalDriver()) MountCameraManager.onBisonDismount();
        super.removePassenger(passenger);
    }

    @Override
    protected float getRiddenSpeed(Player driver)
    {
        return (float) getAttributeValue(isFlying()? Attributes.FLYING_SPEED : Attributes.MOVEMENT_SPEED);
    }

    public void liftOff()
    {
        if (canFly()) jumpFromGround();
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource)
    {
        return !canFly() && super.causeFallDamage(pFallDistance, pMultiplier, pSource);
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


    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    @Override
    public boolean onClimbable()
    {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn)
    {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);

        if (isSaddled()) spawnAtLocation(Items.SADDLE);
    }


    public InteractionResult mobInteract(Player player, InteractionHand hand)
    {
        var stack = player.getItemInHand(hand);

        var stackResult = stack.interactLivingEntity(player, this, hand);
        if (stackResult.consumesAction()) return stackResult;

        // tame
        if (!isTame())
        {
            if (!level().isClientSide && isTameFood(stack))
            {
                stack.shrink(1);
                boolean succ = getRandom().nextInt(5) == 0;
                setTame(succ);
                if (succ) {
                    setOwnerUUID(player.getUUID());
                }
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;
        }

        // heal
        if (getHealthFraction() < 1 && isFood(stack))
        {
            //noinspection ConstantConditions
            //heal(stack.getItem().getFoodProperties(stack, this).getNutrition());
            heal(3);
            playSound(getEatingSound(stack), 0.7f, 1);
            stack.shrink(1);
            return InteractionResult.sidedSuccess(level().isClientSide);
        }


         //saddle up!
        if (isTamedFor(player) && isSaddleable() && !isSaddled() && (stack.getItem() instanceof BigSaddleItem))
        {
            stack.shrink(1);
            equipSaddle(getSoundSource());
            return InteractionResult.sidedSuccess(level().isClientSide);
        } else if (stack.getItem() instanceof SaddleItem){
            return InteractionResult.SUCCESS;
        }

        // give the saddle back!
        if (isTamedFor(player) && isSaddled() && stack.is(Tags.Items.SHEARS))
        {
            spawnAtLocation(ModItems.BIG_SADDLE.get());
            player.playSound(SoundEvents.SHEEP_SHEAR, 1f, 1f);
            setSaddled(false);
            gameEvent(GameEvent.SHEAR, player);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));

            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        // sit!
//        if (isTamedFor(player) && (player.isSecondaryUseActive() || stack.is(Items.BONE))) // "bone sitting" for legacy reasons
//        {
//            if (!level().isClientSide)
//            {
//                navigation.stop();
//                setOrderedToSit(!isOrderedToSit());
//                if (isOrderedToSit()) setTarget(null);
//            }
//            return InteractionResult.sidedSuccess(level().isClientSide);
//        }

         //ride on
        if (isTamedFor(player) && isSaddled() && isAdult() && !isFood(stack))
        {
            if (!level().isClientSide)
            {
                player.startRiding(this);
                navigation.stop();
                setTarget(null);
            }
            setOrderedToSit(false);
            setInSittingPose(false);
            return InteractionResult.sidedSuccess(level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }
}
