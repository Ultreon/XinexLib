package dev.ultreon.mods.xinexlib.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class XinexBlockItem extends BlockItem {
    private final Holder<? extends Block> testBlock;

    @SuppressWarnings("DataFlowIssue")
    public XinexBlockItem(Holder<? extends Block> testBlock, Item.Properties properties) {
        super(null, properties);
        this.testBlock = testBlock;
    }

    @Override
    public @NotNull Block getBlock() {
        return testBlock.value();
    }
}
