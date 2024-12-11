package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.event.JVMShutdownEvent;
import dev.ultreon.mods.xinexlib.event.block.AttemptBlockSetEvent;
import dev.ultreon.mods.xinexlib.event.block.BlockSetEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySpawnEvent;
import dev.ultreon.mods.xinexlib.event.entity.LivingHurtEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseBlockEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseEntityEvent;
import dev.ultreon.mods.xinexlib.event.interact.UseItemEvent;
import dev.ultreon.mods.xinexlib.event.player.AttackEntityEvent;
import dev.ultreon.mods.xinexlib.event.player.PlayerPlaceBlockEvent;
import dev.ultreon.mods.xinexlib.event.server.ServerChatEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.item.XinexBlockItem;
import dev.ultreon.mods.xinexlib.platform.Services;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrar;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrarManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Random;

/*
 This class is part of the common project, meaning it is shared between all supported loaders. Code written here can only
 import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
 common compatible binaries. This means common code cannot directly use loader-specific concepts such as Forge events;
 however, it will be compatible with all supported mod loaders.
*/

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public class CommonClass {
    private CommonClass() {

    }

    /*
     The loader-specific projects are able to import and use any code from the common project. This allows you to
     write the majority of your code here and load it from your loader-specific projects. This example has some
     code that gets invoked by the entry point of the loader-specific projects.
    */

    /// This method is invoked by the provided mod loader when it is ready to load the XinexLib mod.
    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(CommonClass::shutdown));

        if (Services.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            initDev();
        }
    }

    private static void shutdown() {
        EventSystem.MAIN.publish(JVMShutdownEvent.INSTANCE);
    }

    private static void initDev() {
        IRegistrarManager registrarManager = Services.getRegistrarManager(Constants.MOD_ID);
        IRegistrar<Block> blockRegistrar = registrarManager.getRegistrar(Registries.BLOCK);
        var testBlock = blockRegistrar.register("test_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
        var secondBlock = blockRegistrar.register("second_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
        IRegistrar<Item> itemRegistrar = registrarManager.getRegistrar(Registries.ITEM);
        var testItem = itemRegistrar.register("test_item", () -> new Item(new Item.Properties().stacksTo(1)));
        var testBlockItem = itemRegistrar.register("test_block_item", () -> new XinexBlockItem(testBlock, new Item.Properties().stacksTo(1)));
        var secondBlockItem = itemRegistrar.register("second_block_item", () -> new XinexBlockItem(secondBlock, new Item.Properties().stacksTo(1)));

        Constants.LOG.info("The ID for test_block is {}", testBlock.getId());
        Constants.LOG.info("The ID for test_item is {}", testItem.getId());
        Constants.LOG.info("The ID for test_block_item is {}", testBlockItem.getId());
        Constants.LOG.info("The ID for second_block is {}", secondBlock.getId());
        Constants.LOG.info("The ID for second_block_item is {}", secondBlockItem.getId());

        blockRegistrar.load();
        itemRegistrar.load();

        EventSystem.MAIN.on(PlayerPlaceBlockEvent.class, event -> {
            if (!event.getState().is(testBlock)) return;
            Player player = event.getPlayer();
            player.sendSystemMessage(Component.literal("You placed a test block at " + event.getBlockPosition() + "!"));
            if (event.getBlockPosition().getY() < 64) event.cancel();
        });

        EventSystem.MAIN.on(ServerChatEvent.class, event -> {
            if (event.getMessageContent().getString().equalsIgnoreCase("hello")) {
                ServerPlayer player = event.getPlayer();
                player.sendSystemMessage(Component.literal("Hello, " + player.getName().getString()));
            }

            if (event.getMessageContent().getString().equalsIgnoreCase("bye")) {
                ServerPlayer player = event.getPlayer();
                player.sendSystemMessage(Component.literal("Bye, " + player.getName().getString()));
            }

            if (event.getMessageContent().getString().equalsIgnoreCase("block this")) {
                event.cancel();
            }
        });

        final ThreadLocal<Random> random = ThreadLocal.withInitial(Random::new);
        EventSystem.MAIN.on(AttemptBlockSetEvent.class, event -> {
            if (!event.getState().is(secondBlock)) return;
            Random r = random.get();
            r.setSeed(event.getBlockPosition().asLong());
            if (r.nextBoolean()) event.cancel();
        });

        EventSystem.MAIN.on(BlockSetEvent.class, event -> {
            if (!event.getState().is(secondBlock)) return;

            for (Player player : event.getLevel().players()) {
                player.sendSystemMessage(Component.literal("Block set at " + event.getBlockPosition() + "!"));
            }
        });

        EventSystem.MAIN.on(EntitySpawnEvent.class, event -> {
            if (event.getEntity() instanceof Creeper && event.getEntity().position().y > 64)
                event.cancel();
        });

        EventSystem.MAIN.on(EntitySpawnEvent.FreshSpawnEvent.class, event -> {
            if (event.getEntity() instanceof Zombie && event.getEntity().position().y > 64)
                event.cancel();
        });

        EventSystem.MAIN.on(EntitySpawnEvent.ExistingSpawnEvent.class, event -> {
            if (event.getEntity() instanceof Skeleton && event.getEntity().position().y > 64)
                event.cancel();
        });

        EventSystem.MAIN.on(UseBlockEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.sendSystemMessage(Component.literal("You used a block at " + event.getBlockPosition() + "!"));
            }
        });

        EventSystem.MAIN.on(UseItemEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.sendSystemMessage(Component.literal("You used an item at " + event.getPosition() + "!"));
            }
        });

        EventSystem.MAIN.on(UseEntityEvent.class, event -> {
            if (event.getTarget() instanceof Chicken) {
                if (!event.getLevel().isClientSide) event.getTarget().kill();
                event.cancel(InteractionResult.sidedSuccess(event.getLevel().isClientSide));
            }
        });

        EventSystem.MAIN.on(AttackEntityEvent.class, event -> {
            if (event.getVictim() instanceof Chicken) {
                event.getVictim().level().explode(event.getVictim(), event.getVictim().position().x, event.getVictim().position().y, event.getVictim().position().z, 4, Level.ExplosionInteraction.MOB);
                event.cancel();
            }
        });

        EventSystem.MAIN.on(LivingHurtEvent.class, event -> {
            if (event.getVictim() instanceof Pig && event.getAttacker() instanceof Player attacker) {
                attacker.hurt(new DamageSource(event.getDamageSource().typeHolder(), event.getVictim(), event.getAttacker()), event.getAmount());
                event.cancel();
            }
        });

        Constants.LOG.info("The developer mode is enabled!");
    }
}
