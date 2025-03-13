package dev.ultreon.mods.xinexlib.client.event;

import com.mojang.blaze3d.platform.Window;
import dev.ultreon.mods.xinexlib.event.system.Cancelable;

public class WindowToggleFullscreenEvent implements WindowEvent, Cancelable {
    private final Window window;
    private final boolean setFullscreen;
    private boolean canceled;

    public WindowToggleFullscreenEvent(Window window, boolean setFullscreen) {
        this.window = window;
        this.setFullscreen = setFullscreen;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    public boolean isSetFullscreen() {
        return setFullscreen;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel() {
        canceled = true;
    }
}
