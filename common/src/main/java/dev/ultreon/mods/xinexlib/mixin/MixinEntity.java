package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.access.IEntityComponentAccess;
import dev.ultreon.mods.xinexlib.event.entity.EntityLoadEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySaveEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.platform.components.ComponentManager;
import dev.ultreon.mods.xinexlib.platform.components.IComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Mixin(Entity.class)
public abstract class MixinEntity implements IEntityComponentAccess {
    @Unique
    private final Map<ResourceLocation, IComponent<Entity>> xinexlib$components = new HashMap<>();

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void addAdditionalSaveData(CompoundTag pCompound, CallbackInfoReturnable<CompoundTag> cir) {
        Entity entity = (Entity) (Object) this;
        CompoundTag extraData = pCompound.getCompound("XinexLibExtraData");
        EventSystem.MAIN.publish(new EntitySaveEvent(entity, extraData));
        pCompound.put("XinexLibExtraData", extraData);
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void load(CompoundTag pCompound, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        CompoundTag extraData = pCompound.getCompound("XinexLibExtraData");
        if (extraData.isEmpty()) return;
        EventSystem.MAIN.publish(new EntityLoadEvent(entity, extraData));
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(EntityType entityType, Level level, CallbackInfo ci) {
        ComponentManager.installComponents((Entity) (Object) this);
    }

    @Override
    public <T extends IComponent<Entity>> T xinexlib$getComponent(ResourceLocation name, Class<T> clazz) {
        return clazz.cast(xinexlib$components.get(name));
    }

    @Override
    public <T extends IComponent<Entity>> void xinexlib$setComponent(ResourceLocation name, T component) {
        if (component == null) {
            xinexlib$components.remove(name);
            return;
        }
        xinexlib$components.put(name, component);
    }

    @Override
    public Map<ResourceLocation, IComponent<Entity>> xinexlib$getAllComponents() {
        return Collections.unmodifiableMap(xinexlib$components);
    }
}
