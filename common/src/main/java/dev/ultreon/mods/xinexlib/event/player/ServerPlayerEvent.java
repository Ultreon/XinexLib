package dev.ultreon.mods.xinexlib.event.player;

import dev.ultreon.mods.xinexlib.event.entity.EntityEvent;
import dev.ultreon.mods.xinexlib.event.level.ServerLevelEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface ServerPlayerEvent extends PlayerEvent, ServerLevelEvent {
    @Override
    ServerPlayer getPlayer();

    @Override
    default Entity getEntity() {
        return getPlayer();
    }

    @Override
    default Vec3 getPosition() {
        return getEntity().position();
    }

    @Override
    default Level getLevel() {
        return getPlayer().level();
    }

    @Override
    default MinecraftServer getServer() {
        return getPlayer().getServer();
    }

    @Override
    default ServerLevel getServerLevel() {
        return getPlayer().serverLevel();
    }
}
