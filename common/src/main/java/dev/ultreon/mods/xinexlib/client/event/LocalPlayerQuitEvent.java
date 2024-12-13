package dev.ultreon.mods.xinexlib.client.event;

import net.minecraft.client.player.LocalPlayer;

public class LocalPlayerQuitEvent implements LocalPlayerEvent {
    private final LocalPlayer localPlayer;

    public LocalPlayerQuitEvent(LocalPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    @Override
    public LocalPlayer getPlayer() {
        return localPlayer;
    }
}
