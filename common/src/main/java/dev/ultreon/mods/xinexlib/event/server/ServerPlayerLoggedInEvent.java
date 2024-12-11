package dev.ultreon.mods.xinexlib.event.server;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public class ServerPlayerLoggedInEvent {
    private final ServerPlayer player;
    private final Connection connection;

    public ServerPlayerLoggedInEvent(ServerPlayer player, Connection connection) {
        this.player = player;
        this.connection = connection;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServerPlayerLoggedInEvent that = (ServerPlayerLoggedInEvent) o;
        return Objects.equals(player, that.player) && Objects.equals(connection, that.connection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, connection);
    }

    @Override
    public String toString() {
        return "ServerPlayerLoggedInEvent{" +
               "player=" + player + ", " +
               "connection=" + connection + '}';
    }
}
