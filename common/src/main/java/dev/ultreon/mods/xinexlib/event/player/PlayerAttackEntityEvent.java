package dev.ultreon.mods.xinexlib.event.player;

import dev.ultreon.mods.xinexlib.event.level.LevelEvent;
import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class PlayerAttackEntityEvent implements LevelEvent, Cancelable {
    private final Player attacker;
    private final Entity victim;
    private final Level level;
    private boolean canceled;
    private @Nullable EventResult eventResult;

    public PlayerAttackEntityEvent(Player attacker, Level level, Entity victim) {
        this.attacker = attacker;
        this.victim = victim;
        this.level = level;
    }

    public Player getAttacker() {
        return attacker;
    }

    public Entity getVictim() {
        return victim;
    }

    @Override
    public Level getLevel() {
        return level;
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
        if (!(o instanceof PlayerAttackEntityEvent that)) return false;
        return Objects.equals(attacker, that.attacker) && Objects.equals(victim, that.victim) && Objects.equals(level, that.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attacker, victim, level);
    }

    @Override
    public String toString() {
        return "AttackEntityEvent{" +
               "level=" + level +
               ", victim=" + victim +
               ", attacker=" + attacker +
               '}';
    }
}
