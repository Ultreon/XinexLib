package dev.ultreon.mods.xinexlib.client.event;

import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.client.Minecraft;

import java.util.Objects;

public final class ClientStoppingEvent implements Cancelable {
    private final Minecraft client;
    private boolean canceled;

    public ClientStoppingEvent(Minecraft client) {
        this.client = client;
    }

    public Minecraft getClient() {
        return client;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClientStoppingEvent) obj;
        return Objects.equals(this.client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client);
    }

    @Override
    public String toString() {
        return "ClientStoppingEvent[" + "client=" + client + ']';
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }
}
