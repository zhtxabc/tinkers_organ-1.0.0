package com.zhtx.gtmce.event;

import com.mojang.logging.LogUtils;
import com.zhtx.gtmce.Gtmce;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.*;

/**
 * 通过扫描玩家存档 NBT 读取胸腔器官，应用材料属性加成。
 */
@Mod.EventBusSubscriber(modid = Gtmce.MOD_ID)
public class OrganMaterialListener {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<UUID, Set<String>> appliedOrgans = new HashMap<>();
    private static final Map<String, Integer> missingCount = new HashMap<>();
    private static final Map<UUID, Float> flightFuel = new HashMap<>();
    private static final Map<UUID, Float> flightFuelMax = new HashMap<>();
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;
        if (!ModList.get().isLoaded("chestcavity")) return;

        tickCounter++;
        if (tickCounter % 40 != 0) return; // 2秒

        CompoundTag playerData = player.saveWithoutId(new CompoundTag());
        Set<String> currentKeys = new HashSet<>();

        // 直接读取 /ChestCavity/Inventory
        if (playerData.contains("ChestCavity")) {
            CompoundTag cc = playerData.getCompound("ChestCavity");
            if (cc.contains("Inventory")) {
                ListTag inv = cc.getList("Inventory", 10);
                for (int i = 0; i < inv.size(); i++) {
                    CompoundTag slot = inv.getCompound(i);
                    if (!slot.contains("id")) continue;
                    String itemId = slot.getString("id");
                    if (!itemId.startsWith("tinkers_orgen:")) continue;

                    CompoundTag itemTag = slot.contains("tag") ? slot.getCompound("tag") : new CompoundTag();
                    var stats = readStats(itemTag);
                    if (stats == null) continue;
                    String key = itemId + "_slot" + i;
                    currentKeys.add(key);

                    OrganTierEvent organEvent = new OrganTierEvent(player, itemId, stats.materialId,
                            stats.attackDamage, stats.durability, stats.attackSpeed, stats.miningSpeed, stats.tier, i);
                    MinecraftForge.EVENT_BUS.post(organEvent);
                    organEvent.applyDefaultAttributes();
                    appliedOrgans.computeIfAbsent(player.getUUID(), u -> new HashSet<>()).add(key);
                    if (itemId.equals("tinkers_orgen:spine")) {
                        flightFuelMax.put(player.getUUID(), stats.durability * 4f);
                        flightFuel.putIfAbsent(player.getUUID(), stats.durability * 4f);
                    }
                }
            }
        }

        // 清理：连续 2 次扫描都不在才移除
        Set<String> existing = appliedOrgans.computeIfAbsent(player.getUUID(), k -> new HashSet<>());
        Iterator<String> it = existing.iterator();
        while (it.hasNext()) {
            String oldKey = it.next();
            if (!currentKeys.contains(oldKey)) {
                int missed = missingCount.getOrDefault(oldKey, 0) + 1;
                missingCount.put(oldKey, missed);
                if (missed >= 3) {
                    String organId = oldKey.substring(0, oldKey.indexOf("_slot"));
                    OrganTierEvent.removeAllAttributes(player, organId);
                    it.remove();
                    missingCount.remove(oldKey);
                    LOGGER.info("[GTMCE] Removed attributes for {} (missing 3 scans)", organId);
                }
            } else {
                missingCount.remove(oldKey);
            }
        }
        existing.addAll(currentKeys);

        // 飞行消耗耐久
        handleFlightFuel(player, currentKeys);
    }

    private static void handleFlightFuel(ServerPlayer player, Set<String> currentKeys) {
        UUID uuid = player.getUUID();
        boolean flying = player.getAbilities().flying;
        boolean hasSpine = currentKeys.stream().anyMatch(k -> k.contains("tinkers_orgen:spine"));

        if (!hasSpine) {
            if (flying) { player.getAbilities().mayfly = false; player.getAbilities().flying = false; player.onUpdateAbilities(); }
            flightFuel.remove(uuid); flightFuelMax.remove(uuid);
            return;
        }

        // 用玩家持久数据存储燃料
        var pd = player.getPersistentData();
        float fuel = pd.getFloat("tinkers_orgen_flight_fuel");
        float maxFuel = flightFuelMax.getOrDefault(uuid, 2000f);

        if (flying) {
            fuel -= 1f;
            pd.putFloat("tinkers_orgen_flight_fuel", fuel);
            if (fuel <= 0) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
            }
        } else if (fuel < maxFuel) {
            fuel = Math.min(fuel + 1f, maxFuel);
            pd.putFloat("tinkers_orgen_flight_fuel", fuel);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            UUID uuid = player.getUUID();
            appliedOrgans.remove(uuid);
            flightFuel.remove(uuid);
            flightFuelMax.remove(uuid);
        }
    }

    private static StatsReader.Stats readStats(CompoundTag tag) {
        if (!tag.contains("tic_stats")) return null;
        CompoundTag stats = tag.getCompound("tic_stats");
        float atk = stats.getFloat("tconstruct:attack_damage");
        float dur = stats.getFloat("tconstruct:durability");
        float spd = stats.getFloat("tconstruct:attack_speed");
        float min = stats.getFloat("tconstruct:mining_speed");
        int tier = stats.getInt("tconstruct:harvest_tier");
        String mat = "unknown";
        if (tag.contains("tic_materials")) {
            CompoundTag mats = tag.getCompound("tic_materials");
            for (String k : mats.getAllKeys()) {
                String s = mats.getString(k);
                if (!s.isEmpty() && s.contains(":")) { mat = s; break; }
                if (k.contains(":")) { mat = k; break; }
            }
        }
        return new StatsReader.Stats(mat, Math.max(1, tier),
                atk > 0 ? atk : 2f, dur > 0 ? dur : 100f,
                spd > 0 ? spd : 1.2f, min > 0 ? min : 2f);
    }

    public static class StatsReader {
        public static Stats estimate(String mat) {
            String m = mat.toLowerCase();
            if (m.contains("wood") || m.contains("stone") || m.contains("bone") || m.contains("flint"))
                return new Stats(mat, 1, 1.0f, 50f, 1.2f, 1.5f);
            if (m.contains("iron") || m.contains("copper") || m.contains("slimesteel"))
                return new Stats(mat, 2, 3.0f, 200f, 1.4f, 3.0f);
            if (m.contains("cobalt") || m.contains("diamond") || m.contains("hepatizon"))
                return new Stats(mat, 3, 5.0f, 500f, 1.6f, 5.0f);
            if (m.contains("manyullyn") || m.contains("netherite") || m.contains("queen"))
                return new Stats(mat, 4, 8.0f, 1000f, 1.8f, 7.0f);
            return new Stats(mat, 2, 2.0f, 150f, 1.3f, 2.0f);
        }
        public static class Stats {
            public final String materialId;
            public final int tier;
            public final float attackDamage, durability, attackSpeed, miningSpeed;
            Stats(String m, int t, float a, float d, float as, float ms) {
                materialId = m; this.tier = t;
                attackDamage = a; durability = d; attackSpeed = as; miningSpeed = ms;
            }
        }
    }
}
