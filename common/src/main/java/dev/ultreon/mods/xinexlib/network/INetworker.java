package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.IPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Stream;

public interface INetworker {
    <T extends IPacket<T> & IServerEndpoint> void sendToServer(T payload);

    <T extends IPacket<T> & IClientEndpoint> void sendToClient(T payload, ServerPlayer player);

    default <T extends IPacket<T> & IClientEndpoint> void sendToClients(T payload, ServerPlayer... players) {
        for (ServerPlayer player : players) {
            sendToClient(payload, player);
        }
    }

    default <T extends IPacket<T> & IClientEndpoint> void sendToClients(T payload, Iterable<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            sendToClient(payload, player);
        }
    }

    default <T extends IPacket<T> & IClientEndpoint> void sendToClients(T payload, Stream<ServerPlayer> players) {
        players.forEach(player -> sendToClient(payload, player));
    }
}
