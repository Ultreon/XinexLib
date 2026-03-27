package dev.ultreon.mods.xinexlib.access;

import dev.ultreon.mods.xinexlib.components.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import java.util.Map;

public interface EntityComponentAccess {
    <T extends Component<Entity>> T xinexlib$getComponent(Identifier name, Class<T> clazz);

    <T extends Component<Entity>> void xinexlib$setComponent(Identifier name, T component);

    Map<Identifier, Component<Entity>> xinexlib$getAllComponents();
}
