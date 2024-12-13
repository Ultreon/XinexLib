package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.IPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.payload.PayloadFlow;
import net.minecraftforge.network.payload.PayloadProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ForgeNetworkRegistry implements INetworkRegistry {
    private final Map<Class<? extends IPacket>, CustomPacketPayload.Type> typeRegistry = new HashMap<>();
    private final PayloadProtocol<RegistryFriendlyByteBuf, CustomPacketPayload> playProtocol;

    Channel<CustomPacketPayload> channel;
    private final INetworker networker;
    private final String modId;
    private final List<IPacketRegistrant<?>> clientRegistrants = new ArrayList<>();
    private final List<IPacketRegistrant<?>> serverRegistrants = new ArrayList<>();
    private final List<IPacketRegistrant<?>> bidiRegistrants = new ArrayList<>();

    public ForgeNetworkRegistry(String modId, boolean optional, INetworker networker) {
        this.modId = modId;
        this.networker = networker;

        ChannelBuilder named = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(modId, "main"));
        if (optional) named = named.optional();

        playProtocol = named.payloadChannel().play();
    }

    private static PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> client() {
        return ChannelBuilder.named(ResourceLocation.withDefaultNamespace("main/clientbound")).payloadChannel().play().clientbound();
    }

    private static PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> server() {
        return ChannelBuilder.named(ResourceLocation.withDefaultNamespace("main/serverbound")).payloadChannel().play().serverbound();
    }

    private static PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> biDirectional() {
        return ChannelBuilder.named(ResourceLocation.withDefaultNamespace("main/bidirectional")).payloadChannel().play().bidirectional();
    }

    @Override
    public <T extends IPacket<T> & IClientEndpoint> void registerClient(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);

        this.clientRegistrants.add(new ClientPacketRegistrant<T>(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> {
            wrapper.packet.handle(this.networker);
            context.setPacketHandled(true);
        }));
    }

    @Override
    public <T extends IPacket<T> & IServerEndpoint> void registerServer(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);

        this.serverRegistrants.add(new ServerPacketRegistrant<>(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> {
            wrapper.packet.handle(this.networker, context.getSender());
            context.setPacketHandled(true);
        }));
    }

    @Override
    public <T extends IPacket<T> & IServerEndpoint & IClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);

        this.bidiRegistrants.add(new BiDirectionalPacketRegistrant<>(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> {
            wrapper.packet.handle(this.networker, context.getSender());
            context.setPacketHandled(true);
        }));
    }

    @SuppressWarnings("unchecked")
    <T extends IPacket<T>> CustomPacketPayload.Type<PayloadWrapper<T>> getType(Class<T> aClass) {
        return typeRegistry.get(aClass);
    }

    public void finish() {
        PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> clientbound = playProtocol.clientbound();
        for (IPacketRegistrant registrant : clientRegistrants) {
            clientbound.add(registrant.type(), registrant.codec(), registrant.handler());
        }

        PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> serverbound = clientbound.serverbound();
        for (IPacketRegistrant registrant : serverRegistrants) {
            serverbound.add(registrant.type(), registrant.codec(), registrant.handler());
        }

        PayloadFlow<RegistryFriendlyByteBuf, CustomPacketPayload> bidirectional = serverbound.bidirectional();
        for (IPacketRegistrant registrant : bidiRegistrants) {
            bidirectional.add(registrant.type(), registrant.codec(), registrant.handler());
        }

        if (channel != null) {
            throw new IllegalStateException("Networker already initialized");
        }

        channel = bidirectional.build();
    }

    private interface IPacketRegistrant<T extends IPacket<?>> {
        CustomPacketPayload.Type<PayloadWrapper<T>> type();

        StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec();

        BiConsumer<PayloadWrapper<T>, CustomPayloadEvent.Context> handler();
    }

    private record ClientPacketRegistrant<T extends IPacket<?> & IClientEndpoint>(
            CustomPacketPayload.Type<PayloadWrapper<T>> type,
            StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec,
            BiConsumer<PayloadWrapper<T>, CustomPayloadEvent.Context> handler)
            implements IPacketRegistrant<T> {
    }

    private record ServerPacketRegistrant<T extends IPacket<?> & IServerEndpoint>(
            CustomPacketPayload.Type<PayloadWrapper<T>> type,
            StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec,
            BiConsumer<PayloadWrapper<T>, CustomPayloadEvent.Context> handler)
            implements IPacketRegistrant<T> {
    }

    private record BiDirectionalPacketRegistrant<T extends IPacket<?> & IServerEndpoint & IClientEndpoint>(
            CustomPacketPayload.Type<PayloadWrapper<T>> type,
            StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec,
            BiConsumer<PayloadWrapper<T>, CustomPayloadEvent.Context> handler)
            implements IPacketRegistrant<T> {
    }
}
