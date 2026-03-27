package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.access.EntityComponentAccess;
import dev.ultreon.mods.xinexlib.event.entity.EntityLoadEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySaveEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.components.SimpleComponentManager;
import dev.ultreon.mods.xinexlib.components.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(Entity.class)
public abstract class MixinEntity implements EntityComponentAccess {
    @Unique
    private final Map<Identifier, Component<Entity>> xinexlib$components = new HashMap<>();

    @Inject(method = "saveWithoutId", at = @At("HEAD"))
    private void addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        ValueOutput extraData = output.child("XinexLibExtraData");
        EventSystem.MAIN.publish(new EntitySaveEvent(entity, extraData));
    }

    @Inject(method = "load", at = @At("HEAD"))
    private void load(ValueInput pCompound, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        Optional<ValueInput> extraData = pCompound.child("XinexLibExtraData");
        if (extraData.isEmpty()) return;
        EventSystem.MAIN.publish(new EntityLoadEvent(entity, extraData));
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        SimpleComponentManager.installComponents((Entity) (Object) this);
    }

    @Override
    public <T extends Component<Entity>> T xinexlib$getComponent(Identifier name, Class<T> clazz) {
        return clazz.cast(xinexlib$components.get(name));
    }

    @Override
    public <T extends Component<Entity>> void xinexlib$setComponent(Identifier name, T component) {
        if (component == null) {
            xinexlib$components.remove(name);
            return;
        }
        xinexlib$components.put(name, component);
    }

    @Override
    public Map<Identifier, Component<Entity>> xinexlib$getAllComponents() {
        return Collections.unmodifiableMap(xinexlib$components);
    }
}
