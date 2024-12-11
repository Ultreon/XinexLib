package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.nbt.DataKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;

public class EntitySaveEvent implements EntityEvent {
    private final Entity entity;
    private final CompoundTag extraData;

    public EntitySaveEvent(Entity entity, CompoundTag extraData) {
        this.entity = entity;
        this.extraData = extraData;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public Tag getExtraData(DataKey<Entity> key) {
        if (!extraData.contains(key.getKey(entity), Tag.TAG_COMPOUND)) {
            extraData.put(key.getKey(entity), new CompoundTag());
        }
        return extraData.get(key.getKey(entity));
    }

    public void setExtraData(DataKey<Entity> key, Tag data) {
        extraData.put(key.getKey(entity), data);
    }
}
