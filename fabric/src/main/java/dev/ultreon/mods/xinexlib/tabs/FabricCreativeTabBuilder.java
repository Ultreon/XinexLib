package dev.ultreon.mods.xinexlib.tabs;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class FabricCreativeTabBuilder implements CreativeModeTabBuilder {
    private final CreativeModeTab.Builder builder;

    public FabricCreativeTabBuilder() {
        this.builder = FabricCreativeModeTab.builder();
    }

    @Override
    public CreativeModeTabBuilder title(Component name) {
        builder.title(name);
        return this;
    }

    @Override
    public CreativeModeTabBuilder icon(Supplier<ItemStack> icon) {
        builder.icon(icon);
        return this;
    }

    @Override
    public CreativeModeTabBuilder background(Identifier background) {
        builder.backgroundTexture(background);
        return this;
    }

    @Override
    public CreativeModeTabBuilder noScrollBar() {
        builder.noScrollBar();
        return this;
    }

    @Override
    public CreativeModeTabBuilder displayItems(CreativeModeTab.DisplayItemsGenerator generator) {
        builder.displayItems(generator);
        return this;
    }

    @Override
    public CreativeModeTab build() {
        return builder.build();
    }
}
