package dev.ultreon.mods.xinexlib.network.packet;

import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;

public interface IPacketToClient<T extends IPacket<T>> extends IPacket<T>, IClientEndpoint {

}
