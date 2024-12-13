package dev.ultreon.mods.xinexlib.mixin;

import com.mojang.blaze3d.platform.Window;
import dev.ultreon.mods.xinexlib.client.event.ClientRenderTickEvent;
import dev.ultreon.mods.xinexlib.client.event.ClientStartedEvent;
import dev.ultreon.mods.xinexlib.client.event.ClientStoppingEvent;
import dev.ultreon.mods.xinexlib.client.event.LocalPlayerJoinEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {

    @Inject(at = @At("RETURN"), method = "handleLogin")
    private void runTick$return(CallbackInfo ci) {
        EventSystem.MAIN.publish(new LocalPlayerJoinEvent(Minecraft.getInstance().player));
    }
}
