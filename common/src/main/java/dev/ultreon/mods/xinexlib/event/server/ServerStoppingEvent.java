package dev.ultreon.mods.xinexlib.event.server;

import net.minecraft.server.MinecraftServer;

import java.util.Objects;

public final class ServerStoppingEvent {
    private final MinecraftServer server;

    public ServerStoppingEvent(
            MinecraftServer server
    ) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return server;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ServerStoppingEvent) obj;
        return Objects.equals(this.server, that.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server);
    }

    @Override
    public String toString() {
        return "ServerStoppingEvent{" +
               "server=" + server + '}';
    }


}
