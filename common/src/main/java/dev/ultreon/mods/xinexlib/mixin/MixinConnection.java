package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.Env;
import dev.ultreon.mods.xinexlib.EnvExecutor;
import dev.ultreon.mods.xinexlib.client.event.LocalPlayerJoinEvent;
import dev.ultreon.mods.xinexlib.client.event.LocalPlayerQuitEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
@Mixin(Connection.class)
public abstract class MixinConnection {

    @Inject(at = @At("HEAD"), method = "channelInactive")
    private void channelInactive(CallbackInfo ci) {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> EventSystem.MAIN.publish(new LocalPlayerQuitEvent(Minecraft.getInstance().player)));
    }

    @Inject(at = @At("HEAD"), method = "disconnect(Lnet/minecraft/network/DisconnectionDetails;)V")
    private void runTick$return(CallbackInfo ci) {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> EventSystem.MAIN.publish(new LocalPlayerQuitEvent(Minecraft.getInstance().player)));
    }
}
