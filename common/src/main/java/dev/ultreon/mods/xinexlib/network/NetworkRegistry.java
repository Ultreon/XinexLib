package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;

public interface NetworkRegistry {
    <T extends Packet<T> & ClientEndpoint> void registerClient(String name, Class<T> clazz, PacketReader<T> reader);

    <T extends Packet<T> & ServerEndpoint> void registerServer(String name, Class<T> clazz, PacketReader<T> reader);

    <T extends Packet<T> & ServerEndpoint & ClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, PacketReader<T> reader);
}
