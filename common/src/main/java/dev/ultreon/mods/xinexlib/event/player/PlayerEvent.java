package dev.ultreon.mods.xinexlib.event.player;

import dev.ultreon.mods.xinexlib.event.entity.EntityEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface PlayerEvent extends EntityEvent {
    Player getPlayer();

    @Override
    default Entity getEntity() {
        return getPlayer();
    }
}
