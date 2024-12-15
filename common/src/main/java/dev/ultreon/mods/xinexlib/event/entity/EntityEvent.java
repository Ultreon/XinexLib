package dev.ultreon.mods.xinexlib.event.entity;

import dev.ultreon.mods.xinexlib.event.level.LevelEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface EntityEvent extends PositionEvent, LevelEvent {
    Entity getEntity();

    @Override
    default Vec3 getPosition() {
        return getEntity().position();
    }

    @Override
    default Level getLevel() {
        return getEntity().level();
    }
}
