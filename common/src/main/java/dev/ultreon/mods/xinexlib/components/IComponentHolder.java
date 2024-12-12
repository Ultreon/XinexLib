package dev.ultreon.mods.xinexlib.components;

public interface IComponentHolder<O, T extends IComponent<O>> {
    T get(O value);
}
