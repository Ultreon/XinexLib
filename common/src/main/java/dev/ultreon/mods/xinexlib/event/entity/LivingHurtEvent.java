package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class LivingHurtEvent implements Cancelable {
    @Nullable
    private final Entity attacker;
    private final LivingEntity victim;
    private final DamageSource damageSource;
    private boolean canceled;
    private float amount;

    public LivingHurtEvent(@Nullable Entity attacker, LivingEntity victim, DamageSource damageSource, float amount) {
        this.attacker = attacker;
        this.victim = victim;
        this.damageSource = damageSource;
        this.amount = amount;
    }

    @Nullable
    public Entity getAttacker() {
        return attacker;
    }

    public LivingEntity getVictim() {
        return victim;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void cancel() {
        this.amount = 0;
        this.canceled = true;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
