package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.IPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NeoForgeNetworkRegistry implements INetworkRegistry {
    private final Map<Class<? extends IPacket>, CustomPacketPayload.Type> typeRegistry = new HashMap<>();
    private final PayloadRegistrar registrar;
    private final String modId;
    private final Consumer<INetworkRegistry> registrant;
    private INetworker networker;

    public NeoForgeNetworkRegistry(PayloadRegistrar registrar, String modId, Consumer<INetworkRegistry> registrant, INetworker networker) {
        this.registrar = registrar;
        this.modId = modId;
        this.registrant = registrant;
        this.networker = networker;
    }

    @Override
    public <T extends IPacket<T> & IClientEndpoint> void registerClient(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);

        this.registrar.playToClient(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> wrapper.packet.handle(this.networker));
    }

    @Override
    public <T extends IPacket<T> & IServerEndpoint> void registerServer(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);
        
        this.registrar.playToServer(type, StreamCodec.of(
                (buf, packet) -> packet.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        ), (wrapper, context) -> wrapper.packet.handle(this.networker, (ServerPlayer) context.player()));
    }

    @Override
    public <T extends IPacket<T> & IServerEndpoint & IClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, type);
        
        StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec = StreamCodec.of(
                (buf, wrapper) -> wrapper.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        );

        this.registrar.playToServer(type, codec, (wrapper, context) -> wrapper.packet.handle(this.networker, (ServerPlayer) context.player()));
        this.registrar.playToClient(type, codec, (wrapper, context) -> wrapper.packet.handle(this.networker));
    }

    <T extends IPacket<T>> CustomPacketPayload.Type<PayloadWrapper<T>> getType(Class<T> aClass) {
        return (CustomPacketPayload.Type) typeRegistry.get(aClass);
    }
}
