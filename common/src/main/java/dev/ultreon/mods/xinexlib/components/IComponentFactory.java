package dev.ultreon.mods.xinexlib.components;

@FunctionalInterface
public interface IComponentFactory<O, T extends IComponent<O>> {
    T create(O entity);
}
