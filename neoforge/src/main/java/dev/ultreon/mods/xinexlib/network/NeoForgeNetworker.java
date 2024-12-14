package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.function.Consumer;

public class NeoForgeNetworker implements Networker {
    private NeoForgeNetworkRegistry registry;

    public NeoForgeNetworker(IEventBus modEventBus, String modId, Consumer<NetworkRegistry> registrant) {
        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
            PayloadRegistrar registrar = event.registrar("1");
            registrant.accept(registry = new NeoForgeNetworkRegistry(registrar, modId, registrant, this));
        });
    }

    @Override
    public <T extends Packet<T> & ServerEndpoint> void sendToServer(T payload) {
        Minecraft.getInstance().getConnection().send(new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload));
    }

    @Override
    public <T extends Packet<T> & ClientEndpoint> void sendToClient(T payload, ServerPlayer player) {
        player.connection.send(new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload));
    }
}
