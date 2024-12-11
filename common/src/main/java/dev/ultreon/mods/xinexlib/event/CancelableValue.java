package dev.ultreon.mods.xinexlib.event;

import org.jetbrains.annotations.Nullable;

public interface CancelableValue<T> {
    boolean isCanceled();

    void cancel(@Nullable T value);

    @Nullable T get();
}
