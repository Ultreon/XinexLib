package dev.ultreon.mods.xinexlib.components;

public abstract class ComponentBuilder<O, T extends Component<O>> {
    final Class<T> componentClass;

    public ComponentBuilder(Class<T> componentClass) {
        this.componentClass = componentClass;
    }
}
