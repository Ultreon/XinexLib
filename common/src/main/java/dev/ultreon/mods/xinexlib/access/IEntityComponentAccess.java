package dev.ultreon.mods.xinexlib.access;

import dev.ultreon.mods.xinexlib.platform.components.IComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.Map;

public interface IEntityComponentAccess {
    <T extends IComponent<Entity>> T xinexlib$getComponent(ResourceLocation name, Class<T> clazz);

    <T extends IComponent<Entity>> void xinexlib$setComponent(ResourceLocation name, T component);

    Map<ResourceLocation, IComponent<Entity>> xinexlib$getAllComponents();
}
