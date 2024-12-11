package dev.ultreon.mods.xinexlib.access;

import dev.ultreon.mods.xinexlib.platform.components.IComponent;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IBlockEntityComponentAccess {
    <T extends IComponent<BlockEntity>> T getComponent(Class<T> clazz);

    <T extends IComponent<BlockEntity>> void xinexlib$setComponent(Class<T> clazz, T component);
}
