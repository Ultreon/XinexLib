package dev.ultreon.mods.xinexlib.components;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface IComponentManager {
    <T extends IComponent<Entity>> IComponentHolder<Entity, T> registerComponent(String name, EntityComponentBuilder<T> factory);
    <T extends IComponent<Entity>> @Nullable T getComponent(ResourceLocation name, Entity entity, Class<T> clazz);
}
