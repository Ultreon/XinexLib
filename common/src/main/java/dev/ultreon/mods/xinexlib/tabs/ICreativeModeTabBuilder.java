package dev.ultreon.mods.xinexlib.tabs;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public interface ICreativeModeTabBuilder {

    ICreativeModeTabBuilder title(Component name);

    ICreativeModeTabBuilder icon(Supplier<ItemStack> icon);

    ICreativeModeTabBuilder background(ResourceLocation background);

    ICreativeModeTabBuilder noScrollBar();

    ICreativeModeTabBuilder displayItems(CreativeModeTab.DisplayItemsGenerator generator);

    CreativeModeTab build();
}
