package dev.ultreon.mods.xinexlib.event.server;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.Component;

import java.net.SocketAddress;
import java.util.Objects;

public class ServerPlayerLoginBlockedEvent {
    private final SocketAddress socketAddress;
    private final GameProfile gameProfile;
    private final Component reason;

    public ServerPlayerLoginBlockedEvent(SocketAddress socketAddress, GameProfile gameProfile, Component reason) {
        this.socketAddress = socketAddress;
        this.gameProfile = gameProfile;
        this.reason = reason;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public GameProfile getGameProfile() {
        return gameProfile;
    }

    public Component getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServerPlayerLoginBlockedEvent that = (ServerPlayerLoginBlockedEvent) o;
        return Objects.equals(socketAddress, that.socketAddress) && Objects.equals(gameProfile, that.gameProfile) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socketAddress, gameProfile, reason);
    }

    @Override
    public String toString() {
        return "ServerLoginBlockedEvent{" +
                "socketAddress=" + socketAddress +
                ", gameProfile=" + gameProfile +
                ", reason=" + reason +
                '}';
    }
}
