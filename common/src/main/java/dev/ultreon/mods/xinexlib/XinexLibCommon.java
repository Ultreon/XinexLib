package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.access.EntityComponentAccess;
import dev.ultreon.mods.xinexlib.components.*;
import dev.ultreon.mods.xinexlib.event.JVMShutdownEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntityLoadEvent;
import dev.ultreon.mods.xinexlib.event.entity.EntitySaveEvent;
import dev.ultreon.mods.xinexlib.event.system.EventSystem;
import dev.ultreon.mods.xinexlib.nbt.DataKeys;
import dev.ultreon.mods.xinexlib.platform.XinexPlatform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Map;

import static dev.ultreon.mods.xinexlib.Constants.MOD_ID;

/// @author XyperCode
/// @since 0.1.0 (December 10, 2024)
public class XinexLibCommon {
    private XinexLibCommon() {

    }

    /// This method is invoked by the provided mod loader when it is ready to load the XinexLib mod.
    public static void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(XinexLibCommon::shutdown));

        if (XinexPlatform.isDevelopmentEnvironment() && "true".equals(System.getProperty("xinexlib.dev"))) {
            XinexLibDev.initDev();
        }

        EventSystem.MAIN.on(EntitySaveEvent.class, event -> {
        });

        EventSystem.MAIN.on(EntityLoadEvent.class, event -> {
        });
    }

    private static void shutdown() {
        EventSystem.MAIN.publish(JVMShutdownEvent.INSTANCE);
    }

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
