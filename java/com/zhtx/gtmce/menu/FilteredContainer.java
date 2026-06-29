package com.zhtx.gtmce.menu;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.SimpleContainer;

public class FilteredContainer extends SimpleContainer {

    public static final TagKey<Item> FABAO_TAG =
            TagKey.create(Registries.ITEM, new ResourceLocation(Gtmce.MOD_ID, "fabao"));

    public FilteredContainer(int size) {
        super(size);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return stack.is(FABAO_TAG);
    }
}