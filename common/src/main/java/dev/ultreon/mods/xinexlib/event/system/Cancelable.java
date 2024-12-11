package dev.ultreon.mods.xinexlib.event.system;

public interface Cancelable {
    boolean isCanceled();

    void cancel();
}
