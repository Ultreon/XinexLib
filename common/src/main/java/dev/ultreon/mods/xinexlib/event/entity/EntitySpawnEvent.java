package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.event.level.ServerLevelEvent;
import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public abstract class EntitySpawnEvent implements ServerLevelEvent, EntityEvent, Cancelable {
    private final ServerLevel level;
    private final Entity entity;
    private boolean canceled;

    protected EntitySpawnEvent(ServerLevel level, Entity entity) {
        this.level = level;
        this.entity = entity;
    }

    @Override
    public ServerLevel getServerLevel() {
        return level;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    public static class FreshSpawnEvent extends EntitySpawnEvent {
        public FreshSpawnEvent(ServerLevel level, Entity entity) {
            super(level, entity);
        }
    }

    public static class ExistingSpawnEvent extends EntitySpawnEvent {
        public ExistingSpawnEvent(ServerLevel level, Entity entity) {
            super(level, entity);
        }
    }
}
