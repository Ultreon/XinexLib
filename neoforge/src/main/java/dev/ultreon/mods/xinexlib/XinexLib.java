package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.event.SetupEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntityLoadEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySaveEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseBlockEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseItemEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerBreakBlockEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.access.IEntityComponentAccess;
import dev.ultreon.mods.xinexlib.nbt.DataKeys;
import dev.ultreon.mods.xinexlib.platform.NeoForgePlatformHelper;
import dev.ultreon.mods.xinexlib.platform.Services;
import dev.ultreon.mods.xinexlib.platform.components.IComponent;
import dev.ultreon.mods.xinexlib.platform.components.ComponentManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
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

import java.util.Map;

@Mod(Constants.MOD_ID)
public class XinexLib {
    public XinexLib(IEventBus eventBus) {
        /*
         This method is invoked by the NeoForge mod loader when it is ready
         to load your mod. You can access NeoForge and Common code in this
         project.
         Use NeoForge to bootstrap the Common mod.
        */

        NeoForgePlatformHelper platform = (NeoForgePlatformHelper) Services.PLATFORM;
        platform.registerMod(Constants.MOD_ID, eventBus);

        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        NeoForge.EVENT_BUS.register(this);

        eventBus.addListener(FMLCommonSetupEvent.class, fmlCommonSetupEvent -> EventSystem.MAIN.publish(SetupEvent.COMMON));

        if (FMLEnvironment.dist == Dist.CLIENT) {
            XinexLibClient.init();
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
        dev.ultreon.mods.xinexlib.event.player.AttackEntityEvent published = EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.player.AttackEntityEvent(event.getEntity(), event.getEntity().level(), event.getTarget()));
        if (published.isCanceled()) {
            event.setCanceled(true);
        }
    }
}
