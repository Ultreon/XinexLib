package dev.ultreon.mods.xinexlib.dev.network.handler;

import dev.ultreon.mods.xinexlib.dev.network.packets.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class DevClientPlayHandlerImpl extends DevClientPlayHandler {
    @Override
    public void handle(PacketToClient packet) {
        String message = packet.message();
        if (Minecraft.getInstance().player != null) {
            Minecraft.getInstance().player.sendOverlayMessage(Component.literal(message));
        }
    }
}
