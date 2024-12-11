package dev.ultreon.mods.xinexlib.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.ultreon.mods.xinexlib.event.entity.LivingHurtEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @WrapMethod(method = "hurt")
    private boolean place(DamageSource pSource, float pAmount, Operation<Boolean> original) {
        Entity entity = pSource.getEntity();
        LivingHurtEvent event = EventSystem.MAIN.publish(new LivingHurtEvent(entity == null ? pSource.getDirectEntity() : entity, (LivingEntity) (Object) this, pSource, pAmount));
        if (event.isCanceled()) return false;
        return original.call(pSource, event.getAmount());
    }
}
