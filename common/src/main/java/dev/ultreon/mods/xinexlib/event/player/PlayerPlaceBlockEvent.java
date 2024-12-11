package dev.ultreon.mods.xinexlib.event.player;

import dev.ultreon.mods.xinexlib.event.block.PositionalBlockEvent;
import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public final class PlayerPlaceBlockEvent implements PlayerEvent, PositionalBlockEvent, Cancelable {
    private final BlockState state;
    private final Direction placedAgainst;
    private final BlockPos pos;
    private final Level level;
    private final Player entity;
    private final BlockPlaceContext context;
    private boolean canceled;

    public PlayerPlaceBlockEvent(
            BlockState state,
            Direction placedAgainst,
            BlockPos pos,
            Level level,
            Player entity,
            BlockPlaceContext context) {
        this.state = state;
        this.placedAgainst = placedAgainst;
        this.pos = pos;
        this.level = level;
        this.entity = entity;
        this.context = context;
    }

    @Override
    public BlockState getState() {
        return state;
    }

    public Direction getPlacedAgainst() {
        return placedAgainst;
    }

    @Override
    public BlockPos getBlockPosition() {
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

    public BlockPlaceContext getContext() {
        return context;
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerPlaceBlockEvent) obj;
        return Objects.equals(this.state, that.state) &&
               Objects.equals(this.placedAgainst, that.placedAgainst) &&
               Objects.equals(this.pos, that.pos) &&
               Objects.equals(this.level, that.level) &&
               Objects.equals(this.entity, that.entity) &&
               Objects.equals(this.context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, placedAgainst, pos, level, entity, context);
    }

    @Override
    public String toString() {
        return "BlockPlaceEvent{" +
               "state=" + state + ", " +
               "placedAgainst=" + placedAgainst + ", " +
               "pos=" + pos + ", " +
               "level=" + level + ", " +
               "entity=" + entity + '}';
    }
}
