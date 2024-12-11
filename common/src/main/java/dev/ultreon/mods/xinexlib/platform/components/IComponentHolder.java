package dev.ultreon.mods.xinexlib.platform.components;

public interface IComponentHolder<O, T extends IComponent<O>> {
    T get(O value);
}
