package dev.ultreon.mods.xinexlib.network.packet;

import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;

public interface PacketToEither<T extends Packet<T>> extends Packet<T>, ServerEndpoint {

}
