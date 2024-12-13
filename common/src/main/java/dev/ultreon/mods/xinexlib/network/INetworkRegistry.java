package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.IPacket;

public interface INetworkRegistry {
    <T extends IPacket<T> & IClientEndpoint> void registerClient(String name, Class<T> clazz, IPacketReader<T> reader);

    <T extends IPacket<T> & IServerEndpoint> void registerServer(String name, Class<T> clazz, IPacketReader<T> reader);

    <T extends IPacket<T> & IServerEndpoint & IClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, IPacketReader<T> reader);
}
