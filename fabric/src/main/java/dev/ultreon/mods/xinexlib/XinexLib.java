package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.event.interact.UseBlockEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseEntityEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseItemEvent;
import dev.ultreon.mods.xinexlib.event.player.AttackEntityEvent;
import dev.ultreon.mods.xinexlib.event.player.EventResult;
import dev.ultreon.mods.xinexlib.event.player.PlayerBreakBlockEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStartedEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStartingEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStoppedEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStoppingEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class XinexLib implements ModInitializer {
    private static InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        return EventSystem.MAIN.publish(new UseBlockEvent(hitResult, player, world)).get();
    }

    private static void onServerStarting(MinecraftServer server) {
        EventSystem.MAIN.publish(new ServerStartingEvent(server));
    }

    private static void onServerStopping(MinecraftServer server) {
        EventSystem.MAIN.publish(new ServerStoppingEvent(server));
    }

    private static void onServerStarted(MinecraftServer server) {
        EventSystem.MAIN.publish(new ServerStartedEvent(server));
    }

    private static void onServerStopped(MinecraftServer server) {
        EventSystem.MAIN.publish(new ServerStoppedEvent(server));
    }

    private static void afterBlockBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        EventSystem.MAIN.publish(new PlayerBreakBlockEvent(state, pos, world, player));
    }

    private static InteractionResultHolder<ItemStack> interactItem(Player player, Level level, InteractionHand interactionHand) {
        InteractionResult publish = EventSystem.MAIN.publish(new UseItemEvent(player, level, interactionHand).get());
        return new InteractionResultHolder<>(publish, player.getItemInHand(interactionHand));
    }

    private static InteractionResult interactEntity(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        return EventSystem.MAIN.publish(new UseEntityEvent(player, level, interactionHand, entity)).get();
    }

    private static InteractionResult attackEntity(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        AttackEntityEvent publish = EventSystem.MAIN.publish(new AttackEntityEvent(player, level, entity));
        if (publish.isCanceled()) {
            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        PlayerBlockBreakEvents.AFTER.register(XinexLib::afterBlockBreak);

        ServerLifecycleEvents.SERVER_STARTING.register(XinexLib::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(XinexLib::onServerStopping);
        ServerLifecycleEvents.SERVER_STARTED.register(XinexLib::onServerStarted);
        ServerLifecycleEvents.SERVER_STOPPED.register(XinexLib::onServerStopped);

        UseBlockCallback.EVENT.register(XinexLib::interact);
        UseItemCallback.EVENT.register(XinexLib::interactItem);
        UseEntityCallback.EVENT.register(XinexLib::interactEntity);

        AttackEntityCallback.EVENT.register(XinexLib::attackEntity);
    }
}
