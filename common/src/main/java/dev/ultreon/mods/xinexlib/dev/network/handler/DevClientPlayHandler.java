package dev.ultreon.mods.xinexlib.dev.network.handler;

import dev.ultreon.mods.xinexlib.dev.network.packets.PacketToClient;

public abstract class DevClientPlayHandler {
    public abstract void handle(PacketToClient packet);
}
