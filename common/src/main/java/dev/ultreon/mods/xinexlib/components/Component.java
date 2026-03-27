package dev.ultreon.mods.xinexlib.components;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.storage.ValueOutput;

public interface Component<O> {
    void save(ValueOutput tag, HolderLookup.Provider registryLookup);

    void load(CompoundTag tag, HolderLookup.Provider registryLookup);
}
