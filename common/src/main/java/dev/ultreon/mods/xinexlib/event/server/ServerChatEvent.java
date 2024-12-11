package dev.ultreon.mods.xinexlib.event.server;

import dev.ultreon.mods.xinexlib.event.system.Cancelable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

import java.util.Objects;

public final class ServerChatEvent implements Cancelable {
    private final ServerPlayer player;
    private final PlayerChatMessage message;
    private final Component messageContent;
    private boolean canceled = false;

    public ServerChatEvent(ServerPlayer player, PlayerChatMessage message, Component messageContent) {
        this.player = player;
        this.message = message;
        this.messageContent = messageContent;
    }

    public ServerPlayer getPlayer() {
        return this.player;
    }

    public PlayerChatMessage getMessage() {
        return this.message;
    }

    public Component getMessageContent() {
        return this.messageContent;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServerChatEvent that = (ServerChatEvent) o;
        return Objects.equals(player, that.player) && Objects.equals(message, that.message) && Objects.equals(messageContent, that.messageContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, message, messageContent);
    }

    @Override
    public String toString() {
        return "ServerChatEvent{" +
               "player=" + player + ", " +
               "message=" + message + ", " +
               "messageContent=" + messageContent + ", " +
               "}";
    }
}
