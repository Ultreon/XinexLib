package dev.ultreon.mods.xinexlib.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.ultreon.mods.xinexlib.event.entity.LivingHurtEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {
    @WrapMethod(method = "hurtServer")
    private boolean handleHurt(ServerLevel level, DamageSource source, float damage, Operation<Boolean> original) {
        Entity entity = source.getEntity();
        LivingHurtEvent event = EventSystem.MAIN.publish(new LivingHurtEvent(level, entity == null ? source.getDirectEntity() : entity, (LivingEntity) (Object) this, source, damage));
        if (event.isCanceled()) return false;
        return original.call(source, event.getAmount());
    }
}
