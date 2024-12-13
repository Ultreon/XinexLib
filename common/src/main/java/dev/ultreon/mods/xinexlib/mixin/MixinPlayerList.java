package dev.ultreon.mods.xinexlib.mixin;

import com.mojang.authlib.GameProfile;
import dev.ultreon.mods.xinexlib.event.server.*;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.net.SocketAddress;
import java.util.function.Predicate;

@Mixin(PlayerList.class)
public abstract class MixinPlayerList {
    @Shadow
    protected abstract void broadcastChatMessage(PlayerChatMessage pMessage, Predicate<ServerPlayer> pShouldFilterMessageTo, @Nullable ServerPlayer pSender, ChatType.Bound pBoundChatType);

    @Inject(method = "broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V", at = @At(value = "HEAD"), cancellable = true)
    private void handlePlayerChatMessage(PlayerChatMessage pMessage, ServerPlayer pSender, ChatType.Bound pBoundChatType, CallbackInfo ci) {
        ServerChatEvent event = EventSystem.MAIN.publish(new ServerChatEvent(
                pSender,
                pMessage,
                pMessage.decoratedContent()
        ));

        boolean canceled = event.isCanceled();
        if (canceled) {
            ci.cancel();
        }
    }

    @Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"))
    private void handlePlayerJoin(Connection pConnection, ServerPlayer pPlayer, CommonListenerCookie pCookie, CallbackInfo ci) {
        EventSystem.MAIN.publish(new ServerPlayerJoinEvent(
                pPlayer,
                pConnection
        ));
    }

    @Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", remap = false, target = "Lorg/slf4j/Logger;info(Ljava/lang/String;[Ljava/lang/Object;)V"))
    private void handlePlayerLoggedIn(Connection pConnection, ServerPlayer pPlayer, CommonListenerCookie pCookie, CallbackInfo ci) {
        EventSystem.MAIN.publish(new ServerPlayerLoggedInEvent(
                pPlayer,
                pConnection
        ));
    }

    @Inject(method = "remove", at = @At(value = "HEAD"))
    private void handlePlayerLoggedIn(ServerPlayer pPlayer, CallbackInfo ci) {
        EventSystem.MAIN.publish(new ServerPlayerQuitEvent(pPlayer));
    }

    @Inject(method = "canPlayerLogin", at = @At(value = "HEAD"), cancellable = true)
    private void handleLoginVerifying(SocketAddress pSocketAddress, GameProfile pGameProfile, CallbackInfoReturnable<Component> cir) {
        ServerPlayerVerifyLoginEvent event = EventSystem.MAIN.publish(new ServerPlayerVerifyLoginEvent(
                pSocketAddress,
                pGameProfile
        ));

        if (event.isCanceled()) {
            Component reason = event.get();
            if (reason == null) reason = Component.literal("Connection blocked due to unknown reason.");
            EventSystem.MAIN.publish(new ServerPlayerLoginBlockedEvent(pSocketAddress, pGameProfile, reason));
            cir.setReturnValue(reason);
        }
    }

    @Inject(method = "canPlayerLogin", at = @At(value = "RETURN"))
    private void handleLoginBlocked(SocketAddress pSocketAddress, GameProfile pGameProfile, CallbackInfoReturnable<Component> cir) {
        Component reason = cir.getReturnValue();
        if (reason != null)
            EventSystem.MAIN.publish(new ServerPlayerLoginBlockedEvent(pSocketAddress, pGameProfile, reason));
    }
}
