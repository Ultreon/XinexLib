package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.client.NeoForgeXinexLibClient;
import dev.ultreon.mods.xinexlib.event.SetupEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseBlockEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseItemEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerAttackEntityEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerBreakBlockEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.platform.NeoForgePlatform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDestroyBlockEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod(Constants.MOD_ID)
public class NeoForgeXinexLib {
    public NeoForgeXinexLib(IEventBus eventBus) {
        /*
         This method is invoked by the NeoForge mod loader when it is ready
         to load your mod. You can access NeoForge and Common code in this
         project.
         Use NeoForge to bootstrap the Common mod.
        */
        NeoForgePlatform platform = NeoForgePlatform.getPlatform();
        platform.registerMod(Constants.MOD_ID, eventBus);

        Constants.LOG.info("Hello NeoForge world!");
        XinexLibCommon.init();

        NeoForge.EVENT_BUS.register(this);

        eventBus.addListener(FMLCommonSetupEvent.class, fmlCommonSetupEvent -> EventSystem.MAIN.publish(SetupEvent.COMMON));

        if (FMLEnvironment.dist == Dist.CLIENT) {
            NeoForgeXinexLibClient.init();
        }
    }

    @SubscribeEvent
    public void onBlockBreak(LivingDestroyBlockEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            EventSystem.MAIN.publish(new PlayerBreakBlockEvent(event.getState(), event.getPos(), event.getEntity().level(), player));
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.server.ServerStartingEvent(event.getServer()));
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.server.ServerStoppingEvent(event.getServer()));
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.server.ServerStartedEvent(event.getServer()));
    }

    @SubscribeEvent
    public void onServerStopped(ServerStoppedEvent event) {
        EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.server.ServerStoppedEvent(event.getServer()));
    }

    @SubscribeEvent
    public void onBlockUse(PlayerInteractEvent.RightClickBlock event) {
        UseBlockEvent published = EventSystem.MAIN.publish(new UseBlockEvent(event.getHitVec(), event.getEntity(), event.getEntity().level()));
        if (published.isCanceled()) {
            event.setCancellationResult(published.get());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onItemUse(PlayerInteractEvent.RightClickItem event) {
        UseItemEvent published = EventSystem.MAIN.publish(new UseItemEvent(event.getEntity(), event.getEntity().level(), event.getHand()));
        if (published.isCanceled()) {
            event.setCancellationResult(published.get());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        PlayerAttackEntityEvent published = EventSystem.MAIN.publish(new PlayerAttackEntityEvent(event.getEntity(), event.getEntity().level(), event.getTarget()));
        if (published.isCanceled()) {
            event.setCanceled(true);
        }
    }
}
