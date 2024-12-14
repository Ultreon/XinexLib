package dev.ultreon.mods.xinexlib.network.packet;

import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;

public interface PacketToServer<T extends Packet<T>> extends Packet<T>, ServerEndpoint {

}
