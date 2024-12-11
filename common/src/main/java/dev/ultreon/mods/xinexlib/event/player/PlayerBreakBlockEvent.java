package dev.ultreon.mods.xinexlib.event.player;

import dev.ultreon.mods.xinexlib.event.level.LevelEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public final class PlayerBreakBlockEvent implements LevelEvent, PlayerEvent {
    private final BlockState state;
    private final BlockPos pos;
    private final Level level;
    private final Player entity;

    public PlayerBreakBlockEvent(
            BlockState state,
            BlockPos pos,
            Level level,
            Player entity
    ) {
        this.state = state;
        this.pos = pos;
        this.level = level;
        this.entity = entity;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Player getPlayer() {
        return entity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerBreakBlockEvent) obj;
        return Objects.equals(this.state, that.state) &&
               Objects.equals(this.pos, that.pos) &&
               Objects.equals(this.level, that.level) &&
               Objects.equals(this.entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, pos, level, entity);
    }

    @Override
    public String toString() {
        return "BlockBreakEvent{" +
               "state=" + state + ", " +
               "pos=" + pos + ", " +
               "level=" + level + ", " +
               "entity=" + entity + '}';
    }

}
