package dev.ultreon.mods.xinexlib.dev.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.NonNull;

public class TestEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_TICKS_ID = SynchedEntityData.defineId(TestEntity.class, EntityDataSerializers.INT);

    public TestEntity(EntityType<? extends TestEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_TICKS_ID, tickCount);
    }

    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float damage) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(@NonNull ValueInput input) {

    }

    @Override
    protected void addAdditionalSaveData(@NonNull ValueOutput output) {

    }
}
