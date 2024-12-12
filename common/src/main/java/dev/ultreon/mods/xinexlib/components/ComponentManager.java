package dev.ultreon.mods.xinexlib.components;

import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.access.IEntityComponentAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager implements IComponentManager {
    private final String modId;
    private static final Map<ResourceLocation, EntityComponentBuilder<?>> entityfactories = new HashMap<>();
    private static final Map<Class<?>, ResourceLocation> entityIds = new HashMap<>();

    public ComponentManager(String modId) {
        this.modId = modId;
    }

    public static <T extends IComponent<Entity>> T create(Entity entity, ResourceLocation name, Class<T> clazz) {
        IComponent<Entity> o = entityfactories.get(name).factory.create(entity);
        return clazz.cast(o);
    }

    public static void loadComponents(Entity entity, IEntityComponentAccess componentAccess, CompoundTag extraData) {
        for (Map.Entry<ResourceLocation, EntityComponentBuilder<?>> entry : entityfactories.entrySet()) {
            ResourceLocation key = entry.getKey();
            EntityComponentBuilder<?> value = entry.getValue();

            Class<?> componentClass = value.componentClass;
            if (entityIds.containsKey(componentClass) && entityIds.get(componentClass).equals(key)) {
                if (value.target != entity.getType()) {
                    Constants.LOG.warn("Component {} is invalid for entity {}, discarding data!", key, entity.getType());
                    continue;
                }
                IComponent<Entity> o = value.factory.create(entity);
                CompoundTag tag = extraData.getCompound(key.toString());
                o.load(tag, entity.registryAccess());

                componentAccess.xinexlib$setComponent(key, o);
            } else {
                Constants.LOG.warn("Component {} is not registered for entity {}, discarding data!", key, entity.getType());
            }
        }
    }

    public static void installComponents(Entity entity) {
        for (Map.Entry<ResourceLocation, EntityComponentBuilder<?>> entry : entityfactories.entrySet()) {
            ResourceLocation key = entry.getKey();
            EntityComponentBuilder<?> value = entry.getValue();
            Class<?> componentClass = value.componentClass;
            if (entityIds.containsKey(componentClass) && entityIds.get(componentClass).equals(key)) {
                ((IEntityComponentAccess) entity).xinexlib$setComponent(key, value.factory.create(entity));
            }
        }
    }

    @Override
    public <T extends IComponent<Entity>> IComponentHolder<Entity, T> registerComponent(String name, EntityComponentBuilder<T> factory) {
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(modId, name);
        entityfactories.put(resourceLocation, factory);
        Class<T> componentClass = factory.componentClass;
        entityIds.put(componentClass, resourceLocation);
        return entity -> getComponent(resourceLocation, entity, factory.componentClass);
    }

    @Override
    public <T extends IComponent<Entity>> T getComponent(ResourceLocation name, Entity entity, Class<T> clazz) {
        return ((IEntityComponentAccess) entity).xinexlib$getComponent(name, clazz);
    }
}
