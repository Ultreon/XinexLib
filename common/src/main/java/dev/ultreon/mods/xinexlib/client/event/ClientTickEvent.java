package dev.ultreon.mods.xinexlib.client.event;

import net.minecraft.client.Minecraft;

import java.util.Objects;

public abstract class ClientTickEvent implements ClientEvent {
    private final Minecraft client;

    public ClientTickEvent(Minecraft client) {
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
        var that = (ClientTickEvent) obj;
        return Objects.equals(this.client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }

    public static class Pre extends ClientTickEvent {
        public Pre(Minecraft minecraft) {
            super(minecraft);
        }
    }

    public static class Post extends ClientTickEvent {
        public Post(Minecraft minecraft) {
            super(minecraft);
        }
    }
}
