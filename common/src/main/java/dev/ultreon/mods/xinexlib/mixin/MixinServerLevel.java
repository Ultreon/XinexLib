package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.client.event.ClientStoppingEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySpawnEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerLevelSaveEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static java.awt.SystemColor.window;

@Mixin(ServerLevel.class)
public class MixinServerLevel {
    @Inject(method = "addFreshEntity", at = @At("HEAD"), cancellable = true)
    private void addFreshEntity(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        ServerLevel serverLevel = (ServerLevel) (Object) this;
        EntitySpawnEvent event = EventSystem.MAIN.publish(new EntitySpawnEvent.FreshSpawnEvent(serverLevel, pEntity));
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "addWithUUID", at = @At("HEAD"), cancellable = true)
    private void addExistingEntity(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        ServerLevel serverLevel = (ServerLevel) (Object) this;
        EntitySpawnEvent event = EventSystem.MAIN.publish(new EntitySpawnEvent.ExistingSpawnEvent(serverLevel, pEntity));
        if (event.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At("HEAD"), method = "save")
    private void save(ProgressListener pProgress, boolean pFlush, boolean pSkipSave, CallbackInfo ci) {
        EventSystem.MAIN.publish(new ServerLevelSaveEvent((ServerLevel) (Object) this, pProgress, pFlush, pSkipSave));
    }
}
