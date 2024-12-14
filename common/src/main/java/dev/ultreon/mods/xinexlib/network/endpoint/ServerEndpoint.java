package dev.ultreon.mods.xinexlib.network.endpoint;

import dev.ultreon.mods.xinexlib.network.Networker;
import net.minecraft.server.level.ServerPlayer;

public interface ServerEndpoint {
    void handle(Networker connection, ServerPlayer player);
}
