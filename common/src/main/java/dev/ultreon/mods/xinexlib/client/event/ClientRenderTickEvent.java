package dev.ultreon.mods.xinexlib.client.event;

import net.minecraft.client.Minecraft;

import java.util.Objects;

public abstract class ClientRenderTickEvent implements ClientEvent {
    private final Minecraft client;

    public ClientRenderTickEvent(Minecraft client) {
        this.client = client;
    }

    @Override
    public Minecraft getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "ClientRenderTickEvent[" + "client=" + client + ']';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientRenderTickEvent) obj;
        return Objects.equals(this.client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }

    public static class Pre extends ClientRenderTickEvent {
        public Pre(Minecraft minecraft) {
            super(minecraft);
        }
    }

    public static class Post extends ClientRenderTickEvent {
        public Post(Minecraft minecraft) {
            super(minecraft);
        }
    }
}
