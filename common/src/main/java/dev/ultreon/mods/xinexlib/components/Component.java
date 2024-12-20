package dev.ultreon.mods.xinexlib.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public interface Component<O> {
    void save(CompoundTag tag, HolderLookup.Provider registryLookup);

    void load(CompoundTag tag, HolderLookup.Provider registryLookup);
}
