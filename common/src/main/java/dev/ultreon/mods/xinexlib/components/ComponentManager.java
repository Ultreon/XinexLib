package dev.ultreon.mods.xinexlib.components;

import dev.ultreon.mods.xinexlib.platform.services.Platform;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

/// A manager for entity components (and soon maybe others)
///
/// @author <a href="https://github.com/XyperCode">XyperCode</a>
/// @since 0.1.0
/// @see Platform#getComponentManager(String)
public interface ComponentManager {
    <T extends Component<Entity>> ComponentHolder<Entity, T> registerComponent(String name, EntityComponentBuilder<T> factory);
    <T extends Component<Entity>> @Nullable T getComponent(Identifier name, Entity entity, Class<T> clazz);
}
