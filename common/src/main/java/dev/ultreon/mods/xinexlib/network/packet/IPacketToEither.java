package dev.ultreon.mods.xinexlib.network.packet;

import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;

public interface IPacketToEither<T extends IPacket<T>> extends IPacket<T>, IServerEndpoint {

}
