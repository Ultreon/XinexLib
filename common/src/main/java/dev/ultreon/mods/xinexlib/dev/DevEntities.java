package dev.ultreon.mods.xinexlib.dev;

import dev.ultreon.mods.xinexlib.dev.entity.TestEntity;
import dev.ultreon.mods.xinexlib.platform.Services;
import dev.ultreon.mods.xinexlib.registrar.Registrar;
import dev.ultreon.mods.xinexlib.registrar.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class DevEntities {
    private static final Registrar<EntityType<?>> REGISTRAR = Services.getRegistrarManager("xinexlib").getRegistrar(Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<TestEntity>, EntityType<?>> TEST = REGISTRAR.register("test", () -> EntityType.Builder.of(TestEntity::new, MobCategory.AMBIENT)
            .sized(0.5f, 0.5f)
            .build("test"));

    public static void load() {
        REGISTRAR.load();
    }
}
