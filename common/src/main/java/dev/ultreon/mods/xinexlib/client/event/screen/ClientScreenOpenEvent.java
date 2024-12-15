package dev.ultreon.mods.xinexlib.client.event.screen;

import dev.ultreon.mods.xinexlib.client.event.ClientScreenEvent;
import dev.ultreon.mods.xinexlib.event.CancelableValue;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

public class ClientScreenOpenEvent implements ClientScreenEvent, CancelableValue<Screen> {
    private final Screen screen;
    private boolean canceled;
    private @Nullable Screen nextScreen;

    public ClientScreenOpenEvent(Screen screen) {
        this.screen = screen;
    }

    @Override
    public Screen getScreen() {
        return screen;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void cancel(@Nullable Screen value) {
        canceled = true;
        nextScreen = value;
    }

    @Override
    public @Nullable Screen get() {
        return nextScreen;
    }
}
