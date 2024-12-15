package dev.ultreon.mods.xinexlib.mixin;

import dev.ultreon.mods.xinexlib.client.XinexLibClient;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
@Mixin(Connection.class)
public abstract class MixinClientConnection {

    @Inject(at = @At("HEAD"), method = "channelInactive")
    private void channelInactive(CallbackInfo ci) {
        XinexLibClient.onDisconnect();
    }

    @Inject(at = @At("HEAD"), method = "disconnect(Lnet/minecraft/network/DisconnectionDetails;)V")
    private void runTick$return(CallbackInfo ci) {
        XinexLibClient.onDisconnect();
    }
}
