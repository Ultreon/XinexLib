package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.Channel;

import java.util.function.Consumer;

public class ForgeNetworker implements Networker {
    private final ForgeNetworkRegistry registry;
    private final String modId;
    private final Consumer<NetworkRegistry> registrant;
    private final Channel<CustomPacketPayload> channel;

    public ForgeNetworker(String modId, Consumer<NetworkRegistry> registrant) {
        this.modId = modId;
        this.registrant = registrant;

        this.registry = new ForgeNetworkRegistry(modId, false, this);
        this.registrant.accept(this.registry);
        this.registry.finish();

        channel = this.registry.channel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Packet<T> & ServerEndpoint> void sendToServer(T payload) {
        channel.send(new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload), Minecraft.getInstance().getConnection().getConnection());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Packet<T> & ClientEndpoint> void sendToClient(T payload, ServerPlayer player) {
        channel.send(new PayloadWrapper<>(registry.getType((Class<T>) payload.getClass()), payload), player.connection.getConnection());
    }
}
