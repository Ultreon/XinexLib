package dev.ultreon.mods.xinexlib.network.packet;

import net.minecraft.network.RegistryFriendlyByteBuf;

public interface Packet<T extends Packet<T>> {
    void write(RegistryFriendlyByteBuf buffer);
}
