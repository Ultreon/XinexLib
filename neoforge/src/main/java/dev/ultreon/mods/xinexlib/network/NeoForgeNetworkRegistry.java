package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class NeoForgeNetworkRegistry implements NetworkRegistry {
    private final Map<Class<? extends Packet>, CustomPacketPayload.Type> typeRegistry = new HashMap<>();
    private final PayloadRegistrar registrar;
    private final String modId;
    private final Consumer<NetworkRegistry> registrant;
    private Networker networker;

    public NeoForgeNetworkRegistry(PayloadRegistrar registrar, String modId, Consumer<NetworkRegistry> registrant, Networker networker) {
        this.registrar = registrar;
        this.modId = modId;
        this.registrant = registrant;
        this.networker = networker;
    }

    @Override
    public <T extends Packet<T> & ClientEndpoint> void registerClient(String name, Class<T> clazz, PacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);

        this.registrar.playToClient(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> wrapper.packet.handle(this.networker));
    }

    @Override
    public <T extends Packet<T> & ServerEndpoint> void registerServer(String name, Class<T> clazz, PacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);
        
        this.registrar.playToServer(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> wrapper.packet.handle(this.networker, (ServerPlayer) context.player()));
    }

    @Override
    public <T extends Packet<T> & ServerEndpoint & ClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, PacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);
        
        StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec = StreamCodec.of(
                (buf, wrapper) -> wrapper.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        );

        this.registrar.playToServer(type, codec, (wrapper, context) -> wrapper.packet.handle(this.networker, (ServerPlayer) context.player()));
        this.registrar.playToClient(type, codec, (wrapper, context) -> wrapper.packet.handle(this.networker));
    }

    <T extends Packet<T>> CustomPacketPayload.Type<PayloadWrapper<T>> getType(Class<T> aClass) {
        return (CustomPacketPayload.Type) typeRegistry.get(aClass);
    }
}
