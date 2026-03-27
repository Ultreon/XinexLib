package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.nbt.DataKey;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.function.Consumer;

public class EntitySaveEvent implements EntityEvent {
    private final Entity entity;
    private final ValueOutput extraData;

    public EntitySaveEvent(Entity entity, ValueOutput extraData) {
        this.entity = entity;
        this.extraData = extraData;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    public ValueOutput getExtraData(DataKey<Entity> key) {
        return extraData.child(key.getKey(entity));
    }

    public void setExtraData(DataKey<Entity> key, Consumer<ValueOutput> outputConsumer) {
        outputConsumer.accept(extraData.child(key.getKey(entity)));
    }
}
