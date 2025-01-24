package dev.ultreon.mods.xinexlib.mixin;

import com.mojang.blaze3d.platform.Window;
import dev.ultreon.mods.xinexlib.client.event.ClientTickEvent;
import dev.ultreon.mods.xinexlib.client.event.ClientStartedEvent;
import dev.ultreon.mods.xinexlib.client.event.ClientStoppedEvent;
import dev.ultreon.mods.xinexlib.client.event.ClientStoppingEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow @Final private Window window;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo ci) {
        EventSystem.MAIN.publish(new ClientStartedEvent((Minecraft) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "stop", cancellable = true)
    private void shutdown(CallbackInfo ci) {
        ClientStoppingEvent event = EventSystem.MAIN.publish(new ClientStoppingEvent((Minecraft) (Object) this));
        if (event.isCanceled()) {
            GLFW.glfwSetWindowShouldClose(window.getWindow(), false);
            ci.cancel();
        } else {
            EventSystem.MAIN.publish(new ClientStoppedEvent((Minecraft) (Object) this));
        }
    }

    @Inject(at = @At("HEAD"), method = "runTick")
    private void runTick$head(CallbackInfo ci) {
        EventSystem.MAIN.publish(new ClientTickEvent.Pre((Minecraft) (Object) this));
    }

    @Inject(at = @At("RETURN"), method = "runTick")
    private void runTick$return(CallbackInfo ci) {
        EventSystem.MAIN.publish(new ClientTickEvent.Post((Minecraft) (Object) this));
    }
}
