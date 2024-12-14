package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.Stream;

public interface Networker {
    <T extends Packet<T> & ServerEndpoint> void sendToServer(T payload);

    <T extends Packet<T> & ClientEndpoint> void sendToClient(T payload, ServerPlayer player);

    default <T extends Packet<T> & ClientEndpoint> void sendToClients(T payload, ServerPlayer... players) {
        for (ServerPlayer player : players) {
            sendToClient(payload, player);
        }
    }

    default <T extends Packet<T> & ClientEndpoint> void sendToClients(T payload, Iterable<ServerPlayer> players) {
        for (ServerPlayer player : players) {
            sendToClient(payload, player);
        }
    }

    default <T extends Packet<T> & ClientEndpoint> void sendToClients(T payload, Stream<ServerPlayer> players) {
        players.forEach(player -> sendToClient(payload, player));
    }
}
