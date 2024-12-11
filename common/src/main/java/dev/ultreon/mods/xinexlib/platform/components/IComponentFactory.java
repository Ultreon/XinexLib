package dev.ultreon.mods.xinexlib.platform.components;

@FunctionalInterface
public interface IComponentFactory<O, T extends IComponent<O>> {
    T create(O entity);
}
