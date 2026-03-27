package dev.ultreon.mods.xinexlib.tabs;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public interface CreativeModeTabBuilder {

    CreativeModeTabBuilder title(Component name);

    CreativeModeTabBuilder icon(Supplier<ItemStack> icon);

    CreativeModeTabBuilder background(Identifier background);

    CreativeModeTabBuilder noScrollBar();

    CreativeModeTabBuilder displayItems(CreativeModeTab.DisplayItemsGenerator generator);

    CreativeModeTab build();
}
