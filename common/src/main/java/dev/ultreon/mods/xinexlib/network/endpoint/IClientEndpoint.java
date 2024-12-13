package dev.ultreon.mods.xinexlib.network.endpoint;

import dev.ultreon.mods.xinexlib.network.INetworker;

public interface IClientEndpoint {
    void handle(INetworker connection);
}
