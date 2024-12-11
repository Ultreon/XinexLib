package dev.ultreon.mods.xinexlib.event.server;

import com.mojang.authlib.GameProfile;
import dev.ultreon.mods.xinexlib.event.CancelableValue;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.net.SocketAddress;
import java.util.Objects;

public class ServerPlayerVerifyLoginEvent implements CancelableValue<Component> {
    private final SocketAddress address;
    private final GameProfile profile;
    private Component reason = Component.literal("Connection blocked due to unknown reason.");
    private boolean canceled = false;

    public ServerPlayerVerifyLoginEvent(SocketAddress address, GameProfile profile) {
        this.address = address;
        this.profile = profile;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public GameProfile getProfile() {
        return profile;
    }

    @Override
    public void cancel(Component reason) {
        if (reason != null) this.reason = reason;
        this.canceled = true;
    }

    @Override
    public @Nullable Component get() {
        return reason;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServerPlayerVerifyLoginEvent that = (ServerPlayerVerifyLoginEvent) o;
        return Objects.equals(address, that.address) && Objects.equals(profile, that.profile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, profile);
    }

    @Override
    public String toString() {
        return "ServerVerifyLoginEvent{" +
               "address=" + address +
               ", profile=" + profile +
               ", reason=" + reason +
               '}';
    }
}
