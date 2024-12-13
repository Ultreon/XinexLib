package dev.ultreon.mods.xinexlib.event.server;

import dev.ultreon.mods.xinexlib.event.level.ServerLevelEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProgressListener;

import java.util.Objects;

public class ServerLevelSaveEvent implements ServerLevelEvent {
    private final ServerLevel serverLevel;
    private final ProgressListener progress;
    private final boolean flush;
    private final boolean skipSave;

    public ServerLevelSaveEvent(ServerLevel serverLevel, ProgressListener progress, boolean flush, boolean skipSave) {
        this.serverLevel = serverLevel;
        this.progress = progress;
        this.flush = flush;
        this.skipSave = skipSave;
    }

    @Override
    public ServerLevel getServerLevel() {
        return serverLevel;
    }

    public ProgressListener getProgress() {
        return progress;
    }

    public boolean isFlush() {
        return flush;
    }

    public boolean isSkipSave() {
        return skipSave;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ServerLevelSaveEvent that)) return false;
        return flush == that.flush && skipSave == that.skipSave && Objects.equals(serverLevel, that.serverLevel) && Objects.equals(progress, that.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverLevel, progress, flush, skipSave);
    }

    @Override
    public String toString() {
        return "ServerLevelSaveEvent{" +
               "serverLevel=" + serverLevel +
               ", progress=" + progress +
               ", flush=" + flush +
               ", skipSave=" + skipSave +
               '}';
    }
}
