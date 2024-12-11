package dev.ultreon.mods.xinexlib.platform.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public interface IComponent<O> {
    void save(CompoundTag tag, HolderLookup.Provider registryLookup);

    void load(CompoundTag tag, HolderLookup.Provider registryLookup);
}
