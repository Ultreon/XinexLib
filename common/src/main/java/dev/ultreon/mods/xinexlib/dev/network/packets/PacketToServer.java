package dev.ultreon.mods.xinexlib.dev.network.packets;

import dev.ultreon.mods.xinexlib.network.INetworker;
import dev.ultreon.mods.xinexlib.network.packet.IPacketToServer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public record PacketToServer(
    UUID uuid
) implements IPacketToServer<PacketToServer> {
    public static PacketToServer read(RegistryFriendlyByteBuf buf) {
        return new PacketToServer(buf.readUUID());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    @Override
    public void handle(INetworker connection, ServerPlayer player) {
        player.sendSystemMessage(Component.nullToEmpty("Hello, the UUID is: " + uuid));
    }
}
