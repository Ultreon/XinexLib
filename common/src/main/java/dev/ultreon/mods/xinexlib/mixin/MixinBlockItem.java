package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.event.player.PlayerPlaceBlockEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class MixinBlockItem {
    @Shadow
    protected abstract BlockState getPlacementState(BlockPlaceContext pContext);

    @Shadow
    public abstract Block getBlock();

    @Inject(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BlockItem;getPlacementState(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"), cancellable = true)
    private void place(BlockPlaceContext context, CallbackInfoReturnable<InteractionResult> cir) {
        BlockState placementState = getPlacementState(context);
        PlayerPlaceBlockEvent event = EventSystem.MAIN.publish(new PlayerPlaceBlockEvent(
                placementState != null ? placementState : getBlock().defaultBlockState(),
                context.getClickedFace(),
                context.getClickedPos(),
                context.getLevel(),
                context.getPlayer(),
                context
        ));

        boolean canceled = event.isCanceled();
        if (canceled) {
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
