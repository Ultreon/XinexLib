package dev.ultreon.mods.xinexlib;

import com.mojang.brigadier.arguments.StringArgumentType;
import dev.ultreon.mods.xinexlib.access.EntityComponentAccess;
import dev.ultreon.mods.xinexlib.components.*;
import dev.ultreon.mods.xinexlib.dev.DevEntities;
import dev.ultreon.mods.xinexlib.dev.network.packets.PacketToClient;
import dev.ultreon.mods.xinexlib.event.JVMShutdownEvent;
import dev.ultreon.mods.xinexlib.event.block.AttemptBlockSetEvent;
import dev.ultreon.mods.xinexlib.event.block.BlockSetEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntityLoadEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySaveEvent;
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
import dev.ultreon.mods.xinexlib.nbt.DataKeys;
import dev.ultreon.mods.xinexlib.network.Networker;
import dev.ultreon.mods.xinexlib.platform.XinexPlatform;
import dev.ultreon.mods.xinexlib.registrar.Registrar;
import dev.ultreon.mods.xinexlib.registrar.RegistrarManager;
import net.minecraft.commands.Commands;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;
import java.util.Random;

/*
 This class is part of the common project, meaning it is shared between all supported loaders. Code written here can only
 import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
 common compatible binaries. This means common code cannot directly use loader-specific concepts such as Forge events;
 however, it will be compatible with all supported mod loaders.
*/

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public class XinexLibCommon {
    private XinexLibCommon() {

    }

    /*
     The loader-specific projects are able to import and use any code from the common project. This allows you to
     write the majority of your code here and load it from your loader-specific projects. This example has some
     code that gets invoked by the entry point of the loader-specific projects.
    */

    /// This method is invoked by the provided mod loader when it is ready to load the XinexLib mod.
    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(XinexLibCommon::shutdown));

        if (XinexPlatform.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            initDev();
        }

        EventSystem.MAIN.on(EntitySaveEvent.class, (event) -> {
            EntityComponentAccess entity = (EntityComponentAccess) event.getEntity();
            CompoundTag extraData = event.getExtraData(DataKeys.COMPONENTS) instanceof CompoundTag tag ? tag : new CompoundTag();
            Map<ResourceLocation, Component<Entity>> components = entity.xinexlib$getAllComponents();
            for (Map.Entry<ResourceLocation, Component<Entity>> entry : components.entrySet()) {
                CompoundTag componentTag = extraData.getCompound(entry.getKey().toString());
                entry.getValue().save(componentTag, event.getEntity().registryAccess());
                extraData.put(entry.getKey().toString(), componentTag);
            }

            event.setExtraData(DataKeys.COMPONENTS, extraData);
        });

        EventSystem.MAIN.on(EntityLoadEvent.class, (event) -> {
            EntityComponentAccess componentAccess = (EntityComponentAccess) event.getEntity();
            SimpleComponentManager.loadComponents(event.getEntity(), componentAccess, event.getExtraData(DataKeys.COMPONENTS) instanceof CompoundTag tag ? tag : new CompoundTag());
        });
    }

    private static void shutdown() {
        EventSystem.MAIN.publish(JVMShutdownEvent.INSTANCE);
    }

    private static void initDev() {
        RegistrarManager registrarManager = XinexPlatform.getRegistrarManager(Constants.MOD_ID);
        Registrar<Block> blockRegistrar = registrarManager.getRegistrar(Registries.BLOCK);
        var testBlock = blockRegistrar.register("test_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
        var secondBlock = blockRegistrar.register("second_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops()));
        Registrar<Item> itemRegistrar = registrarManager.getRegistrar(Registries.ITEM);
        var testItem = itemRegistrar.register("test_item", () -> new Item(new Item.Properties().stacksTo(1)));
        var testBlockItem = itemRegistrar.register("test_block_item", () -> new XinexBlockItem(testBlock, new Item.Properties().stacksTo(1)));
        var secondBlockItem = itemRegistrar.register("second_block_item", () -> new XinexBlockItem(secondBlock, new Item.Properties().stacksTo(1)));
        Registrar<CreativeModeTab> creativeModeTabRegistrar = registrarManager.getRegistrar(Registries.CREATIVE_MODE_TAB);
        var testTab = creativeModeTabRegistrar.register("test_tab", () -> XinexPlatform.creativeTabBuilder().icon(() -> new ItemStack(testBlockItem)).displayItems((itemDisplayParameters, output) -> {
            output.accept(new ItemStack(testItem));
            output.accept(new ItemStack(testBlockItem));
            output.accept(new ItemStack(secondBlockItem));
        }).title(net.minecraft.network.chat.Component.literal("Test Tab")).build());

        Constants.LOG.info("The ID for test_block is {}", testBlock.getId());
        Constants.LOG.info("The ID for test_item is {}", testItem.getId());
        Constants.LOG.info("The ID for test_block_item is {}", testBlockItem.getId());
        Constants.LOG.info("The ID for second_block is {}", secondBlock.getId());
        Constants.LOG.info("The ID for second_block_item is {}", secondBlockItem.getId());
        Constants.LOG.info("The ID for test_tab is {}", testTab.getId());

        blockRegistrar.load();
        itemRegistrar.load();
        creativeModeTabRegistrar.load();

        DevEntities.load();

        EventSystem.MAIN.on(PlayerPlaceBlockEvent.class, event -> {
            if (!event.getState().is(testBlock)) return;
            Player player = event.getPlayer();
            player.sendSystemMessage(net.minecraft.network.chat.Component.literal("You placed a test block at " + event.getBlockPosition() + "!"));
            if (event.getBlockPosition().getY() < 64) event.cancel();
        });

        EventSystem.MAIN.on(ServerChatEvent.class, event -> {
            if (event.getMessageContent().getString().equalsIgnoreCase("hello")) {
                ServerPlayer player = event.getPlayer();
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Hello, " + player.getName().getString()));
            }

            if (event.getMessageContent().getString().equalsIgnoreCase("bye")) {
                ServerPlayer player = event.getPlayer();
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Bye, " + player.getName().getString()));
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
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Block set at " + event.getBlockPosition() + "!"));
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
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("You used a block at " + event.getBlockPosition() + "!"));
            }
        });

        EventSystem.MAIN.on(UseItemEvent.class, event -> {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("You used an item at " + event.getPosition() + "!"));
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

        class TestyComponent implements Component<Entity> {
            private String name = "John Doe";

            public TestyComponent(String name) {
                this.name = name;
            }

            @Override
            public void save(CompoundTag tag, HolderLookup.Provider registryLookup) {
                tag.putString("name", name);
            }

            @Override
            public void load(CompoundTag tag, HolderLookup.Provider registryLookup) {
                name = tag.getString("name");
                if (name.isBlank()) name = "John Doe";
            }
        }

        ComponentManager componentManager = XinexPlatform.getComponentManager(Constants.MOD_ID);
        ComponentHolder<Entity, TestyComponent> testy = componentManager.registerComponent("testy", new EntityComponentBuilder<TestyComponent>(TestyComponent.class)
                .factory(entity -> new TestyComponent("John Doe"))
                .target(EntityType.PLAYER));

        EventSystem.MAIN.on(ServerChatEvent.class, event -> {
            if (event.getMessageContent().getString().equalsIgnoreCase("what is my name?")) {
                ServerPlayer player = event.getPlayer();
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("Your player name is " + testy.get(player).name));
                event.cancel();
            }

            if (event.getMessageContent().getString().toLowerCase().startsWith("my name is ")) {
                ServerPlayer player = event.getPlayer();
                testy.get(player).name = event.getMessageContent().getString().substring("my name is ".length());
                event.cancel();
            }
        });

        Constants.LOG.info("The developer mode is enabled!");

        Networker networker = XinexPlatform.createNetworker(Constants.MOD_ID, iNetworkRegistry -> {
            iNetworkRegistry.registerClient("packet2client", PacketToClient.class, PacketToClient::read);
//            iNetworkRegistry.registerServer("packet2server", PacketToServer.class, PacketToServer::read);
        });

        XinexPlatform.registerCommand((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("xinex-dev:packets")
                    .then(Commands.literal("packet")
                            .then(Commands.argument("message", StringArgumentType.greedyString())
                                    .executes(context -> {
                                        try {
                                            String string = StringArgumentType.getString(context, "message");
                                            PacketToClient packet = new PacketToClient(string);
                                            networker.sendToClient(packet, context.getSource().getPlayerOrException());
                                        } catch (Exception e) {
                                            Constants.LOG.error("Failed to send packet", e);
                                        }
                                        return 1;
                                    })
                            )
                    )
            );
        });
    }
}
