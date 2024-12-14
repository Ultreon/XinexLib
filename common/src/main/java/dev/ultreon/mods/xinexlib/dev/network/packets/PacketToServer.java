package dev.ultreon.mods.xinexlib.dev.network.packets;

import dev.ultreon.mods.xinexlib.network.Networker;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public record PacketToServer(
    UUID uuid
) implements dev.ultreon.mods.xinexlib.network.packet.PacketToServer<PacketToServer> {
    public static PacketToServer read(RegistryFriendlyByteBuf buf) {
        return new PacketToServer(buf.readUUID());
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    @Override
    public void handle(Networker connection, ServerPlayer player) {
        player.sendSystemMessage(Component.nullToEmpty("Hello, the UUID is: " + uuid));
    }
}
