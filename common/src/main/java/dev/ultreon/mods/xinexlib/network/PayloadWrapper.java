package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

class PayloadWrapper<T extends Packet<?>> implements CustomPacketPayload {
    private final Type<PayloadWrapper<T>> type;
    T packet;

    PayloadWrapper(Type<PayloadWrapper<T>> type) {
        this.type = type;
    }

    PayloadWrapper(Type<PayloadWrapper<T>> type, T packet) {
        this.type = type;
        this.packet = packet;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return type;
    }

    public void write(RegistryFriendlyByteBuf object) {
        packet.write(object);
    }
}
