package dev.ultreon.mods.xinexlib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.ultreon.mods.xinexlib.client.event.ClientScreenOpenEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class FabricMinecraftMixin {
    @Inject(method = "setScreen", at = @At(value = "HEAD"), cancellable = true)
    public void setScreen(Screen screen, CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) LocalRef<Screen> guiScreen) {
        ClientScreenOpenEvent publish = EventSystem.MAIN.publish(new ClientScreenOpenEvent(screen));
        if (publish.isCanceled()) {
            if (publish.get() != null) {
                guiScreen.set(publish.get());
            }
            ci.cancel();
        }
    }
}
