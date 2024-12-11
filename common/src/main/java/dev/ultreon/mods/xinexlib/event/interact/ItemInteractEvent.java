package dev.ultreon.mods.xinexlib.event.interact;

import dev.ultreon.mods.xinexlib.event.CancelableValue;
import net.minecraft.world.ItemInteractionResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ItemInteractEvent implements CancelableValue<ItemInteractionResult> {
    protected boolean canceled;
    protected ItemInteractionResult interactionResult;

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public @NotNull ItemInteractionResult get() {
        return interactionResult;
    }

    @Override
    public void cancel(@Nullable ItemInteractionResult value) {
        if (value != null) this.interactionResult = value;
        this.canceled = true;
    }
}
