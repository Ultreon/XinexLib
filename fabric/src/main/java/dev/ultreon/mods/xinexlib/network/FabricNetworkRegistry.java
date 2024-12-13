package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.network.endpoint.IClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.IServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.IPacket;
import dev.ultreon.mods.xinexlib.platform.Services;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class FabricNetworkRegistry implements INetworkRegistry {
    private final String modId;
    private final INetworker networker;
    private Map<Class<? extends IPacket>, CustomPacketPayload.Type<PayloadWrapper>> typeRegistry = new HashMap<>();

    public FabricNetworkRegistry(INetworker networker, String modId) {
        this.networker = networker;
        this.modId = modId;
    }

    @Override
    public <T extends IPacket<T> & IClientEndpoint> void registerClient(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, (CustomPacketPayload.Type) type);

        PayloadTypeRegistry.playS2C().register(type,
                StreamCodec.of(
                        (buf, wrapper) -> wrapper.write(buf),
                        (buf) -> new PayloadWrapper<>(type, reader.read(buf))
                ));
        if (Services.getEnv() == Env.CLIENT)
            ClientPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(this.networker));
    }

    @Override
    public <T extends IPacket<T> & IServerEndpoint> void registerServer(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, (CustomPacketPayload.Type) type);

        PayloadTypeRegistry.playC2S().register(type,
                StreamCodec.of(
                        (buf, wrapper) -> wrapper.write(buf),
                        (buf) -> new PayloadWrapper<>(type, reader.read(buf))
                ));
        ServerPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(this.networker, context.player()));
    }

    @Override
    public <T extends IPacket<T> & IServerEndpoint & IClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, IPacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, (CustomPacketPayload.Type) type);

        StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec = StreamCodec.of(
                (buf, wrapper) -> wrapper.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        );
        PayloadTypeRegistry.playS2C().register(type, codec);
        PayloadTypeRegistry.playC2S().register(type, codec);
        if (Services.getEnv() == Env.CLIENT)
            ClientPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(networker));
        ServerPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(networker, context.player()));
    }

    @SuppressWarnings("unchecked")
    <T extends IPacket<T>> CustomPacketPayload.Type<PayloadWrapper<T>> getType(Class<T> aClass) {
        return (CustomPacketPayload.Type) typeRegistry.get(aClass);
    }
}
