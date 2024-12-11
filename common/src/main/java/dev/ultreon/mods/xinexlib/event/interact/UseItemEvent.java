package dev.ultreon.mods.xinexlib.event.interact;

import dev.ultreon.mods.xinexlib.event.block.ItemStackEvent;
import dev.ultreon.mods.xinexlib.event.level.LevelEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class UseItemEvent extends InteractEvent implements PlayerEvent, LevelEvent, ItemStackEvent {
    private final Player player;
    private final Level level;
    private final InteractionHand hand;
    private final ItemStack stack;

    public UseItemEvent(Player player, Level level, InteractionHand hand) {
        super(hand);
        this.player = player;
        this.level = level;
        this.hand = hand;
        this.stack = player.getItemInHand(hand);
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public InteractionHand getHand() {
        return hand;
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UseItemEvent that = (UseItemEvent) o;
        return Objects.equals(player, that.player) && Objects.equals(level, that.level) && hand == that.hand && Objects.equals(stack, that.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, level, hand, stack);
    }

    @Override
    public String toString() {
        return "UseItemEvent{" +
               "player=" + player +
               ", level=" + level +
               ", hand=" + hand +
               ", stack=" + stack +
               '}';
    }
}
