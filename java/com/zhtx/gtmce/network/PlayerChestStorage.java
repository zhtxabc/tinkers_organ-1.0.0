package com.zhtx.gtmce.network;

import com.zhtx.gtmce.menu.FilteredContainer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 每个玩家独立的箱子物品存储（会话内持久化）
 */
public class PlayerChestStorage {
    private static final Map<UUID, SimpleContainer> STORAGE = new HashMap<>();

    public static SimpleContainer getOrCreate(Player player) {
        return STORAGE.computeIfAbsent(player.getUUID(), k -> new FilteredContainer(27));
    }

    /** 当玩家离开世界或服务器停止时调用 */
    public static void remove(Player player) {
        STORAGE.remove(player.getUUID());
    }
}
