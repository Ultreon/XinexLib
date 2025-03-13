package dev.ultreon.mods.xinexlib.client.event;

import com.mojang.blaze3d.platform.Window;
import dev.ultreon.mods.xinexlib.event.system.Cancelable;

public class WindowCloseEvent implements WindowEvent, Cancelable {
    private final Window window;
    private boolean canceled;

    public WindowCloseEvent(Window window) {
        this.window = window;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }
}
