package dev.ultreon.mods.xinexlib.network.endpoint;

import dev.ultreon.mods.xinexlib.network.Networker;

public interface ClientEndpoint {
    void handle(Networker connection);
}
