package com.zhtx.gtmce.menu;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class FilteredSlot extends net.minecraft.world.inventory.Slot {
    public FilteredSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(FilteredContainer.FABAO_TAG);
    }
}