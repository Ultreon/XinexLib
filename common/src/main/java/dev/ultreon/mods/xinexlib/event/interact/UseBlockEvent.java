package dev.ultreon.mods.xinexlib.event.interact;

import dev.ultreon.mods.xinexlib.event.block.PositionalBlockEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Objects;

public class UseBlockEvent extends InteractEvent implements PositionalBlockEvent, PlayerEvent {
    private final BlockPos pos;
    private final Player player;
    private final BlockState state;
    private final Level level;

    public UseBlockEvent(BlockHitResult hitResult, Player player, Level level) {
        super(player.getUsedItemHand());
        this.pos = hitResult.getBlockPos();
        this.player = player;
        this.state = level.getBlockState(pos);
        this.level = level;
    }

    @Override
    public BlockPos getBlockPosition() {
        return pos;
    }

    @Override
    public BlockState getState() {
        return state;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UseBlockEvent that = (UseBlockEvent) o;
        return Objects.equals(pos, that.pos) && Objects.equals(state, that.state) && Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, state, level);
    }

    @Override
    public String toString() {
        return "UseBlockEvent{" +
               "pos=" + pos +
               ", player=" + player +
               ", state=" + state +
               ", level=" + level +
               '}';
    }
}
