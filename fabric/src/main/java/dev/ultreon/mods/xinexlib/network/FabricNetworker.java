package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class FabricNetworker implements Networker {
    private final Consumer<NetworkRegistry> registrant;
    private final FabricNetworkRegistry registry;

    public FabricNetworker(String modId, Consumer<NetworkRegistry> registrant) {
        this.registrant = registrant;
        this.registry = new FabricNetworkRegistry(this, modId);
        registrant.accept(registry);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Packet<T> & ServerEndpoint> void sendToServer(T payload) {
        ClientPlayNetworking.send(new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Packet<T> & ClientEndpoint> void sendToClient(T payload, ServerPlayer player) {
        ServerPlayNetworking.send(player, new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload));
    }
}
