package dev.ultreon.mods.xinexlib.event.level;

import dev.ultreon.mods.xinexlib.event.player.ServerEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public interface ServerLevelEvent extends LevelEvent, ServerEvent {
    ServerLevel getServerLevel();

    @Override
    default Level getLevel() {
        return getServerLevel();
    }

    @Override
    default MinecraftServer getServer() {
        return getServerLevel().getServer();
    }
}
