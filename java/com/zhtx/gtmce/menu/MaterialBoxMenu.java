package com.zhtx.gtmce.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MaterialBoxMenu extends AbstractContainerMenu {

    private static final int ROWS = 3;
    private final Container container;

    public MaterialBoxMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, new FilteredContainer(9 * ROWS));
    }

    public MaterialBoxMenu(int id, Inventory playerInv, Container container) {
        super(ModMenuTypes.MATERIAL_BOX_MENU.get(), id);
        this.container = container;

        // 0-26: 箱子槽位（使用过滤槽）
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < 9; c++) {
                addSlot(new FilteredSlot(container, c + r * 9, 8 + c * 18, 18 + r * 18));
            }
        }

        // 27-53: 玩家背包
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 9; c++) {
                addSlot(new Slot(playerInv, c + r * 9 + 9, 8 + c * 18, 84 + r * 18));
            }
        }

        // 54-62: 快捷栏
        for (int c = 0; c < 9; c++) {
            addSlot(new Slot(playerInv, c, 8 + c * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return stack;
        ItemStack raw = slot.getItem();
        stack = raw.copy();

        if (index < 9 * ROWS) {
            // 箱子 → 背包
            if (!moveItemStackTo(raw, 9 * ROWS, slots.size(), true))
                return ItemStack.EMPTY;
        } else {
            // 背包 → 箱子（只允许标签物品）
            if (!raw.is(FilteredContainer.FABAO_TAG))
                return ItemStack.EMPTY;
            if (!moveItemStackTo(raw, 0, 9 * ROWS, false))
                return ItemStack.EMPTY;
        }

        if (raw.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
