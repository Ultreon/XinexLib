package dev.ultreon.mods.xinexlib.network.endpoint;

import dev.ultreon.mods.xinexlib.network.INetworker;
import net.minecraft.server.level.ServerPlayer;

public interface IServerEndpoint {
    void handle(INetworker connection, ServerPlayer player);
}
