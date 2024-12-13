package dev.ultreon.mods.xinexlib.client.event;

import dev.ultreon.mods.xinexlib.event.player.PlayerEvent;
import net.minecraft.client.player.LocalPlayer;

public interface LocalPlayerEvent extends PlayerEvent {
    LocalPlayer getPlayer();
}
