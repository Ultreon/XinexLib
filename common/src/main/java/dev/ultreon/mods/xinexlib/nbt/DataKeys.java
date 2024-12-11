package dev.ultreon.mods.xinexlib.nbt;

import dev.ultreon.mods.xinexlib.Constants;
import net.minecraft.world.entity.Entity;

public class DataKeys {
    public static final DataKey<Entity> COMPONENTS = DataKey.of(Constants.MOD_ID, "components");
}
