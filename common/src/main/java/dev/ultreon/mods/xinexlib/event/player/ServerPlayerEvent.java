package dev.ultreon.mods.xinexlib.event.player;

import dev.ultreon.mods.xinexlib.event.entity.EntityEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface ServerPlayerEvent extends PlayerEvent, ServerEvent {
    @Override
    ServerPlayer getPlayer();

    @Override
    default MinecraftServer getServer() {
        return getPlayer().getServer();
    }
}
