package dev.ultreon.mods.xinexlib.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;

public interface IPacket<T extends IPacket<T>> {
    void write(RegistryFriendlyByteBuf buffer);
}
