package dev.ultreon.mods.xinexlib.platform.components;

public class ComponentBuilder<O, T extends IComponent<O>> {
    final Class<T> componentClass;

    public ComponentBuilder(Class<T> componentClass) {
        this.componentClass = componentClass;
    }
}
