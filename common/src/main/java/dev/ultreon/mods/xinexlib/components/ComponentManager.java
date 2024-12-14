package dev.ultreon.mods.xinexlib.components;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface ComponentManager {
    <T extends Component<Entity>> ComponentHolder<Entity, T> registerComponent(String name, EntityComponentBuilder<T> factory);
    <T extends Component<Entity>> @Nullable T getComponent(ResourceLocation name, Entity entity, Class<T> clazz);
}
