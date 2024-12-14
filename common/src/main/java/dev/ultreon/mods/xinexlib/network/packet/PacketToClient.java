package dev.ultreon.mods.xinexlib.network.packet;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;

public interface PacketToClient<T extends Packet<T>> extends Packet<T>, ClientEndpoint {

}
