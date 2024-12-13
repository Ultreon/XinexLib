package dev.ultreon.mods.xinexlib.network;

import dev.ultreon.mods.xinexlib.network.packet.IPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;

@FunctionalInterface
public interface IPacketReader<T extends IPacket<?>> {
    T read(RegistryFriendlyByteBuf buffer);
}
