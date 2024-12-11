package dev.ultreon.mods.xinexlib.event.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface EntityEvent extends PositionEvent {
    Entity getEntity();

    @Override
    default Vec3 getPosition() {
        return getEntity().position();
    }
}
