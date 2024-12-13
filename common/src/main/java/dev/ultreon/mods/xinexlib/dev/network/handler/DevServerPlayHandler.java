package dev.ultreon.mods.xinexlib.dev.network.handler;

import dev.ultreon.mods.xinexlib.dev.network.packets.PacketToServer;
import dev.ultreon.mods.xinexlib.network.PacketInfo;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class DevServerPlayHandler {
    public void handle(PacketToServer packet, PacketInfo packetInfo) {
        UUID uuid = packet.uuid();
        packetInfo.sender().sendSystemMessage(Component.nullToEmpty("Hello, the UUID is: " + uuid));
    }
}
