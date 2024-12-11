package dev.ultreon.mods.xinexlib.event.interact;

import dev.ultreon.mods.xinexlib.event.level.LevelEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class UseEntityEvent extends InteractEvent implements PlayerEvent, LevelEvent {
    private final Player player;
    private final Level level;
    private final Entity target;

    public UseEntityEvent(Player player, Level level, InteractionHand hand, Entity target) {
        super(hand);
        this.player = player;
        this.level = level;
        this.target = target;
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public Entity getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UseEntityEvent that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(player, that.player) && Objects.equals(level, that.level) && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, level, target);
    }

    @Override
    public String toString() {
        return "UseItemEvent{" +
               "player=" + player +
               ", level=" + level +
               ", hand=" + hand +
               ", target=" + target +
               '}';
    }
}
