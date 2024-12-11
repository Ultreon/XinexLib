package dev.ultreon.mods.xinexlib.event.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockStateEvent extends BlockEvent {
    BlockState getState();

    @Override
    default Block getBlock() {
        return getState().getBlock();
    }
}
