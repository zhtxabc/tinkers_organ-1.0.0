package com.zhtx.gtmce.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * 当玩家天逆珠内空间有法宝物品时击杀僵尸触发。
 * KubeJS 可监听此事件添加额外掉落。
 *
 * <pre>
 * // KubeJS 示例:
 * ForgeEvents.onEvent('com.zhtx.gtmce.event.OrganDropEvent', event => {
 *     event.addDrop(Item.of('minecraft:diamond').toItemStack())
 * })
 * </pre>
 */
public class OrganDropEvent extends Event {

    private final Player player;
    private final LivingDropsEvent parentEvent;
    private final List<ItemStack> extraDrops = new ArrayList<>();

    public OrganDropEvent(Player player, LivingDropsEvent parentEvent) {
        this.player = player;
        this.parentEvent = parentEvent;
    }

    public Player getPlayer() {
        return player;
    }

    public LivingDropsEvent getParentEvent() {
        return parentEvent;
    }

    public void addDrop(ItemStack stack) {
        extraDrops.add(stack);
    }

    public List<ItemStack> getExtraDrops() {
        return extraDrops;
    }
}
