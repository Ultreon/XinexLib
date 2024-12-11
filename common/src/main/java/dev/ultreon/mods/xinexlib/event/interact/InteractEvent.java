package dev.ultreon.mods.xinexlib.event.interact;

import dev.ultreon.mods.xinexlib.event.CancelableValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class InteractEvent implements CancelableValue<InteractionResult> {
    protected final InteractionHand hand;
    protected boolean canceled;
    protected InteractionResult interactionResult = InteractionResult.PASS;

    public InteractEvent(InteractionHand hand) {
        this.hand = hand;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public InteractionResult get() {
        return interactionResult;
    }

    @Override
    public void cancel(@Nullable InteractionResult value) {
        if (value != null) this.interactionResult = value;
        this.canceled = true;
    }

    public InteractionHand getHand() {
        return hand;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InteractEvent that)) return false;
        return hand == that.hand;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hand);
    }
}
