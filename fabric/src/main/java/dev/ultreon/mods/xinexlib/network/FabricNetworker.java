package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.IPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public class FabricNetworker implements INetworker {
    private final Consumer<INetworkRegistry> registrant;
    private final FabricNetworkRegistry registry;

    public FabricNetworker(String modId, Consumer<INetworkRegistry> registrant) {
        this.registrant = registrant;
        this.registry = new FabricNetworkRegistry(this, modId);
        registrant.accept(registry);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IPacket<T> & IServerEndpoint> void sendToServer(T payload) {
        ClientPlayNetworking.send(new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IPacket<T> & IClientEndpoint> void sendToClient(T payload, ServerPlayer player) {
        ServerPlayNetworking.send(player, new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload));
    }
}
