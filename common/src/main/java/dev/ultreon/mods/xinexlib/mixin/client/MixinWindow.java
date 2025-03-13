package dev.ultreon.mods.xinexlib.mixin.client;

import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.Window;
import dev.ultreon.mods.xinexlib.client.event.WindowCloseEvent;
import dev.ultreon.mods.xinexlib.client.event.WindowSetIconEvent;
import dev.ultreon.mods.xinexlib.client.event.WindowToggleFullscreenEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.server.packs.PackResources;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Window.class)
public abstract class MixinWindow {
    @Shadow public abstract boolean isFullscreen();

    @Inject(
            at = @At("HEAD"),
            method = "setIcon",
            cancellable = true
    )
    public void setIcon(PackResources pPackResources, IconSet pIconSet, CallbackInfo ci) {
        WindowSetIconEvent publish = EventSystem.MAIN.publish(new WindowSetIconEvent((Window) (Object) this, pPackResources, pIconSet));
        if (publish.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "toggleFullScreen",
            cancellable = true
    )
    public void toggleFullScreen(CallbackInfo ci) {
        WindowToggleFullscreenEvent publish = EventSystem.MAIN.publish(new WindowToggleFullscreenEvent((Window) (Object) this, !isFullscreen()));
        if (publish.isCanceled()) {
            ci.cancel();
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onResize",
            cancellable = true
    )
    public void onResize(long handle, int width, int height, CallbackInfo ci) {
        WindowToggleFullscreenEvent publish = EventSystem.MAIN.publish(new WindowToggleFullscreenEvent((Window) (Object) this, !isFullscreen()));
        if (publish.isCanceled()) {
            GLFW.glfwSetWindowSize(handle, width, height);
            ci.cancel();
        }
    }

    @Inject(
            at = @At("RETURN"),
            method = "shouldClose",
            cancellable = true
    )
    public void onClose(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            WindowCloseEvent publish = EventSystem.MAIN.publish(new WindowCloseEvent((Window) (Object) this));
            if (publish.isCanceled()) {
                GLFW.glfwSetWindowShouldClose(((Window) (Object) this).getWindow(), false);
                cir.setReturnValue(false);
            }
        }
    }
}
