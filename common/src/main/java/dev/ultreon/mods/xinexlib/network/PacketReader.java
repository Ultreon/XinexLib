package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.packet.Packet;
import net.minecraft.network.RegistryFriendlyByteBuf;

@FunctionalInterface
public interface PacketReader<T extends Packet<?>> {
    T read(RegistryFriendlyByteBuf buffer);
}
