package dev.ultreon.mods.xinexlib.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public interface ClientScreenEvent extends ClientEvent {
    Screen getScreen();

    @Override
    default Minecraft getClient() {
        return Minecraft.getInstance();
    }
}
