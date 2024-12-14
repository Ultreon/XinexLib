package dev.ultreon.mods.xinexlib.client;

import dev.ultreon.mods.xinexlib.CommonClass;
import dev.ultreon.mods.xinexlib.Constants;
import dev.ultreon.mods.xinexlib.event.interact.UseBlockEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseEntityEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseItemEvent;
import dev.ultreon.mods.xinexlib.event.player.AttackEntityEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerBreakBlockEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStartedEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStartingEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStoppedEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerStoppingEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
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

public class XinexLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        ClientClass.init();
    }
}
