package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.event.block.AttemptBlockSetEvent;
import dev.ultreon.mods.xinexlib.event.block.BlockSetEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class MixinLevel {
    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", at = @At("HEAD"), cancellable = true)
    private void setBlock(BlockPos pPos, BlockState pNewState, int pFlags, CallbackInfoReturnable<Boolean> cir) {
        Level serverLevel = (Level) (Object) this;
        AttemptBlockSetEvent publish = EventSystem.MAIN.publish(new AttemptBlockSetEvent(serverLevel, pPos, pNewState, pFlags));
        if (publish.isCanceled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", at = @At("RETURN"), cancellable = true)
    private void setBlock$return(BlockPos pPos, BlockState pNewState, int pFlags, CallbackInfoReturnable<Boolean> cir) {
        Level serverLevel = (Level) (Object) this;
        EventSystem.MAIN.publish(new BlockSetEvent(serverLevel, pPos, pNewState, pFlags));
    }
}
