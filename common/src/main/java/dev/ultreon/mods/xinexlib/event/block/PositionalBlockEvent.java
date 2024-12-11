package dev.ultreon.mods.xinexlib.event.block;

import dev.ultreon.mods.xinexlib.event.level.LevelEvent;
import net.minecraft.core.BlockPos;

public interface PositionalBlockEvent extends LevelEvent, BlockStateEvent {
    BlockPos getBlockPosition();
}
