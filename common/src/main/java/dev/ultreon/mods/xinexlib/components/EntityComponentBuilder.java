package dev.ultreon.mods.xinexlib.components;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class EntityComponentBuilder<T extends Component<Entity>> extends ComponentBuilder<Entity, T> {
    EntityType<?> target;
    ComponentFactory<Entity, T> factory;

    public EntityComponentBuilder(Class<T> componentClass) {
        super(componentClass);
    }

    public EntityComponentBuilder<T> target(EntityType<?> target) {
        this.target = target;
        return this;
    }

    public EntityComponentBuilder<T> factory(ComponentFactory<Entity, T> factory) {
        this.factory = factory;
        return this;
    }
}
