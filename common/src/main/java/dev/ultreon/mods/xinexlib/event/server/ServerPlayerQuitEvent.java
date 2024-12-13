package dev.ultreon.mods.xinexlib.event.server;

import dev.ultreon.mods.xinexlib.event.player.ServerPlayerEvent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class ServerPlayerQuitEvent implements ServerPlayerEvent {
    private final ServerPlayer player;

    public ServerPlayerQuitEvent(ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServerPlayerQuitEvent that = (ServerPlayerQuitEvent) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(player);
    }

    @Override
    public String toString() {
        return "ServerPlayerQuitEvent{" +
               "player=" + player + '}';
    }
}
