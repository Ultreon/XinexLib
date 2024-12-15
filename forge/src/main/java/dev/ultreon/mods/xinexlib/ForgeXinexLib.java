package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.client.ForgeXinexLibClient;
import dev.ultreon.mods.xinexlib.event.SetupEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseBlockEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseEntityEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseItemEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerAttackEntityEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerBreakBlockEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeXinexLib {

    public ForgeXinexLib() {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        XinexLibCommon.init();

        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ForgeXinexLibClient::clientInit);
    }

    @SubscribeEvent
    public void onBlockBreak(LivingDestroyBlockEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player player) {
            EventSystem.MAIN.publish(new PlayerBreakBlockEvent(event.getState(), event.getPos(), event.getEntity().level(), player));
        }
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        EventSystem.MAIN.publish(SetupEvent.COMMON);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.server.ServerStartingEvent(event.getServer()));
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        EventSystem.MAIN.publish(new dev.ultreon.mods.xinexlib.event.server.ServerStoppingEvent(event.getServer()));
    }

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
    public void onEntityUse(PlayerInteractEvent.EntityInteract event) {
        UseEntityEvent published = EventSystem.MAIN.publish(new UseEntityEvent(event.getEntity(), event.getEntity().level(), event.getHand(), event.getTarget()));
        if (published.isCanceled()) {
            event.setCancellationResult(published.get());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        PlayerAttackEntityEvent published = EventSystem.MAIN.publish(new PlayerAttackEntityEvent(event.getEntity(), event.getEntity().level(), event.getTarget()));
        if (published.isCanceled()) {
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
        }
    }
}
