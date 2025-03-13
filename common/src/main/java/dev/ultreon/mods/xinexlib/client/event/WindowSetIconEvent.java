package dev.ultreon.mods.xinexlib.client.event;

import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.Window;
import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.server.packs.PackResources;

import java.util.Objects;

public final class WindowSetIconEvent implements WindowEvent, Cancelable {
    private final Window window;
    private final PackResources packResources;
    private final IconSet iconSet;

    private boolean canceled;

    public WindowSetIconEvent(Window window, PackResources packResources, IconSet iconSet) {
        this.window = window;
        this.packResources = packResources;
        this.iconSet = iconSet;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    public PackResources packResources() {
        return packResources;
    }

    public IconSet iconSet() {
        return iconSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (WindowSetIconEvent) obj;
        return Objects.equals(this.window, that.window) &&
                Objects.equals(this.packResources, that.packResources) &&
                Objects.equals(this.iconSet, that.iconSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(window, packResources, iconSet);
    }

    @Override
    public String toString() {
        return "WindowSetIconEvent[" +
                "getWindow=" + window + ", " +
                "packResources=" + packResources + ", " +
                "iconSet=" + iconSet + ']';
    }

}
