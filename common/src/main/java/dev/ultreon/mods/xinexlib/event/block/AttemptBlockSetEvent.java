package dev.ultreon.mods.xinexlib.event.block;

import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class AttemptBlockSetEvent implements PositionalBlockEvent, Cancelable {
    private final BlockState state;
    private final BlockPos pos;
    private final Level level;
    private final int flags;
    private final BlockState oldState;
    private boolean canceled;

    public AttemptBlockSetEvent(Level level, BlockPos pos, BlockState state, int flags) {
        this.state = state;
        this.pos = pos;
        this.level = level;
        this.flags = flags;
        this.oldState = level.getBlockState(pos);
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public BlockState getState() {
        return state;
    }

    public BlockState getOldState() {
        return oldState;
    }

    @Override
    public BlockPos getBlockPosition() {
        return pos;
    }

    public int getFlags() {
        return flags;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttemptBlockSetEvent that = (AttemptBlockSetEvent) o;
        return Objects.equals(state, that.state) && Objects.equals(pos, that.pos) && Objects.equals(level, that.level) && Objects.equals(oldState, that.oldState) && Objects.equals(flags, that.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, pos, level, oldState, flags);
    }

    @Override
    public String toString() {
        return "ServerAttemptBlockSetEvent{" +
               "oldState=" + oldState +
               ", level=" + level +
               ", pos=" + pos +
               ", state=" + state +
               ", flags=" + Integer.toBinaryString(flags) +
               '}';
    }
}
