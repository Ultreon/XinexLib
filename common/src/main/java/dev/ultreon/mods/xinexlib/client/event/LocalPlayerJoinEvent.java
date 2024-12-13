package dev.ultreon.mods.xinexlib.client.event;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

public class LocalPlayerJoinEvent implements LocalPlayerEvent {
    private final LocalPlayer localPlayer;

    public LocalPlayerJoinEvent(LocalPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    @Override
    public LocalPlayer getPlayer() {
        return localPlayer;
    }
}
