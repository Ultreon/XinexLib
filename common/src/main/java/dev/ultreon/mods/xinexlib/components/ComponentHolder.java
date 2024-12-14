package dev.ultreon.mods.xinexlib.components;

public interface ComponentHolder<O, T extends Component<O>> {
    T get(O value);
}
