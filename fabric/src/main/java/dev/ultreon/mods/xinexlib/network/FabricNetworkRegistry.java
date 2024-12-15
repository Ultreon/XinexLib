package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.network.endpoint.ClientEndpoint;
import dev.ultreon.mods.xinexlib.network.endpoint.ServerEndpoint;
import dev.ultreon.mods.xinexlib.network.packet.Packet;
import dev.ultreon.mods.xinexlib.platform.XinexPlatform;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class FabricNetworkRegistry implements NetworkRegistry {
    private final String modId;
    private final Networker networker;
    private Map<Class<? extends Packet>, CustomPacketPayload.Type<PayloadWrapper>> typeRegistry = new HashMap<>();

    public FabricNetworkRegistry(Networker networker, String modId) {
        this.networker = networker;
        this.modId = modId;
    }

    @Override
    public <T extends Packet<T> & ClientEndpoint> void registerClient(String name, Class<T> clazz, PacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, (CustomPacketPayload.Type) type);

        PayloadTypeRegistry.playS2C().register(type,
                StreamCodec.of(
                        (buf, wrapper) -> wrapper.write(buf),
                        (buf) -> new PayloadWrapper<>(type, reader.read(buf))
                ));
        if (XinexPlatform.getEnv() == Env.CLIENT)
            ClientPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(this.networker));
    }

    @Override
    public <T extends Packet<T> & ServerEndpoint> void registerServer(String name, Class<T> clazz, PacketReader<T> reader) {
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
    public <T extends Packet<T> & ServerEndpoint & ClientEndpoint> void registerBiDirectional(String name, Class<T> clazz, PacketReader<T> reader) {
        var type = new CustomPacketPayload.Type<PayloadWrapper<T>>(ResourceLocation.fromNamespaceAndPath(modId, name));
        this.typeRegistry.put(clazz, (CustomPacketPayload.Type) type);

        StreamCodec<RegistryFriendlyByteBuf, PayloadWrapper<T>> codec = StreamCodec.of(
                (buf, wrapper) -> wrapper.write(buf),
                (buf) -> new PayloadWrapper<>(type, reader.read(buf))
        );
        PayloadTypeRegistry.playS2C().register(type, codec);
        PayloadTypeRegistry.playC2S().register(type, codec);
        if (XinexPlatform.getEnv() == Env.CLIENT)
            ClientPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(networker));
        ServerPlayNetworking.registerGlobalReceiver(type, (packet, context) -> packet.packet.handle(networker, context.player()));
    }

    @SuppressWarnings("unchecked")
    <T extends Packet<T>> CustomPacketPayload.Type<PayloadWrapper<T>> getType(Class<T> aClass) {
        return (CustomPacketPayload.Type) typeRegistry.get(aClass);
    }
}
