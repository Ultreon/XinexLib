package dev.ultreon.mods.xinexlib.event.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class BlockSetEvent implements PositionalBlockEvent {
    private final Level level;
    private final BlockPos pos;
    private final BlockState state;
    private final int flags;

    public BlockSetEvent(Level level, BlockPos pos, BlockState state, int flags) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.flags = flags;
    }

    @Override
    public BlockState getState() {
        return state;
    }

    @Override
    public BlockPos getBlockPosition() {
        return pos;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    public int getFlags() {
        return flags;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BlockSetEvent that = (BlockSetEvent) o;
        return Objects.equals(level, that.level) && Objects.equals(pos, that.pos) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, pos, state);
    }

    @Override
    public String toString() {
        return "ServerBlockSetEvent{" +
               "level=" + level +
               ", pos=" + pos +
               ", state=" + state +
               '}';
    }
}
