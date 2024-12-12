package dev.ultreon.mods.xinexlib.components;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BlockEntityComponentBuilder<T extends IComponent<BlockEntity>> extends ComponentBuilder<BlockEntity, T> {
    BlockEntityType<?> target;
    IComponentFactory<BlockEntity, T> factory;

    public BlockEntityComponentBuilder(Class<T> componentClass) {
        super(componentClass);
    }

    public BlockEntityComponentBuilder<T> target(BlockEntityType<?> target) {
        this.target = target;
        return this;
    }

    public BlockEntityComponentBuilder<T> factory(IComponentFactory<BlockEntity, T> factory) {
        this.factory = factory;
        return this;
    }
}
