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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.Map;

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

        EventSystem.MAIN.on(EntityLoadEvent.class, event -> {
            EntityComponentAccess componentAccess = (EntityComponentAccess) event.getEntity();
            SimpleComponentManager.loadComponents(event.getEntity(), componentAccess, event.getExtraData(DataKeys.COMPONENTS) instanceof CompoundTag tag ? tag : new CompoundTag());
        });
    }

    private static void shutdown() {
        EventSystem.MAIN.publish(JVMShutdownEvent.INSTANCE);
    }

}
