package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.nbt.DataKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueInput;

import java.util.Optional;

public class EntityLoadEvent implements EntityEvent {
    private final Entity entity;
    private final Optional<ValueInput> extraData;

    public EntityLoadEvent(Entity entity, Optional<ValueInput> extraData) {
        this.entity = entity;
        this.extraData = extraData;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public Optional<ValueInput> getExtraData(DataKey<Entity> key) {
        return extraData.flatMap(input -> input.child(key.getKey(entity)));
    }
}
