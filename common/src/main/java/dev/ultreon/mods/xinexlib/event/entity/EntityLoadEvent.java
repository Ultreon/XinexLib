package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.nbt.DataKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;

public class EntityLoadEvent implements EntityEvent {
    private final Entity entity;
    private final CompoundTag extraData;

    public EntityLoadEvent(Entity entity, CompoundTag extraData) {
        this.entity = entity;
        this.extraData = extraData;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public Tag getExtraData(DataKey<Entity> key) {
        return extraData.get(key.getKey(entity));
    }
}
