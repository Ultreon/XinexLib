package dev.ultreon.mods.xinexlib.components;

@FunctionalInterface
public interface ComponentFactory<O, T extends Component<O>> {
    T create(O entity);
}
