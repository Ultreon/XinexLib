package dev.ultreon.mods.xinexlib.components;

public class ComponentBuilder<O, T extends Component<O>> {
    final Class<T> componentClass;

    public ComponentBuilder(Class<T> componentClass) {
        this.componentClass = componentClass;
    }
}
