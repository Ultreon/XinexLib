package dev.ultreon.mods.xinexlib.tabs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class FabricCreativeTabBuilder implements ICreativeModeTabBuilder {
    private final CreativeModeTab.Builder builder;

    public FabricCreativeTabBuilder() {
        this.builder = FabricItemGroup.builder();
    }

    @Override
    public ICreativeModeTabBuilder title(Component name) {
        builder.title(name);
        return this;
    }

    @Override
    public ICreativeModeTabBuilder icon(Supplier<ItemStack> icon) {
        builder.icon(icon);
        return this;
    }

    @Override
    public ICreativeModeTabBuilder background(ResourceLocation background) {
        builder.backgroundTexture(background);
        return this;
    }

    @Override
    public ICreativeModeTabBuilder noScrollBar() {
        builder.noScrollBar();
        return this;
    }

    @Override
    public ICreativeModeTabBuilder displayItems(CreativeModeTab.DisplayItemsGenerator generator) {
        builder.displayItems(generator);
        return this;
    }

    @Override
    public CreativeModeTab build() {
        return builder.build();
    }
}
