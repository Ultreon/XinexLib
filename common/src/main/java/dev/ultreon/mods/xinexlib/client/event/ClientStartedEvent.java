package dev.ultreon.mods.xinexlib.client.event;

import net.minecraft.client.Minecraft;

import java.util.Objects;

public final class ClientStartedEvent implements ClientEvent {
    private final Minecraft client;

    public ClientStartedEvent(Minecraft client) {
        this.client = client;
    }

    @Override
    public Minecraft getClient() {
        return client;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientStartedEvent) obj;
        return Objects.equals(this.client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }

    @Override
    public String toString() {
        return "ClientStartedEvent[" + "client=" + client + ']';
    }

}
