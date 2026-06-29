package com.zhtx.gtmce.event;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

import java.util.*;

/**
 * 器官属性应用事件 — 根据匠魂材料实际属性计算器官加成。
 */
public class OrganTierEvent extends Event {

    private final Player player;
    private final String organId;
    private final String materialId;
    private final float attackDamage, durability, attackSpeed, miningSpeed;
    private final int materialTier, slotIndex;

    public OrganTierEvent(Player player, String organId, String materialId,
                          float attackDamage, float durability, float attackSpeed,
                          float miningSpeed, int materialTier, int slotIndex) {
        this.player = player;
        this.organId = organId;
        this.materialId = materialId;
        this.attackDamage = attackDamage;
        this.durability = durability;
        this.attackSpeed = attackSpeed;
        this.miningSpeed = miningSpeed;
        this.materialTier = materialTier;
        this.slotIndex = slotIndex;
    }

    public Player getPlayer() { return player; }
    public String getOrganId() { return organId; }
    public String getMaterialId() { return materialId; }
    public float getAttackDamage() { return attackDamage; }
    public float getDurability() { return durability; }
    public float getAttackSpeed() { return attackSpeed; }
    public float getMiningSpeed() { return miningSpeed; }
    public int getMaterialTier() { return materialTier; }

    // UUID 基数 + slot偏移
    private static final long B_HEART = 0xa1b2c3d410010001L;
    private static final long B_MUSCLE_ATK = 0xa1b2c3d410070007L;
    private static final long B_MUSCLE_SPD = 0xa1b2c3d410080008L;
    private static final long B_RIB_DEF = 0xa1b2c3d410090009L;
    private static final long B_RIB_TOUGH = 0xa1b2c3d410090019L;
    private static final long B_SPINE_DEF = 0xa1b2c3d410100010L;
    private static final long B_SPINE_TOUGH = 0xa1b2c3d410100020L;
    private static final long B_SPINE_NERV = 0xa1b2c3d410110011L;
    private static final long B_APPENDIX = 0xa1b2c3d410140014L;

    private UUID u(long base) { return new UUID(base, base + slotIndex * 100L); }

    public void applyDefaultAttributes() {
        switch (organId) {
            case "tinkers_orgen:heart":
                add(Attributes.MAX_HEALTH, u(B_HEART), durability * 0.0001f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            case "tinkers_orgen:muscle":
                add(Attributes.ATTACK_DAMAGE, u(B_MUSCLE_ATK), attackDamage * 0.005, AttributeModifier.Operation.MULTIPLY_TOTAL);
                add(Attributes.MOVEMENT_SPEED, u(B_MUSCLE_SPD), attackSpeed * 0.003f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            case "tinkers_orgen:rib":
                add(Attributes.ARMOR, u(B_RIB_DEF), durability * 0.04f, AttributeModifier.Operation.ADDITION);
                add(Attributes.ARMOR_TOUGHNESS, u(B_RIB_TOUGH), durability * 0.04f, AttributeModifier.Operation.ADDITION);
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 100, 3, false, false));
                break;
            case "tinkers_orgen:spine":
                add(Attributes.ARMOR, u(B_SPINE_DEF), durability * 0.025f, AttributeModifier.Operation.ADDITION);
                add(Attributes.ARMOR_TOUGHNESS, u(B_SPINE_TOUGH), durability * 0.025f, AttributeModifier.Operation.ADDITION);
                add(Attributes.ATTACK_SPEED, u(B_SPINE_NERV), attackSpeed * 0.01f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();
                break;
            case "tinkers_orgen:lung":
                add(Attributes.MAX_HEALTH, u(B_HEART + 1000), durability * 0.00003f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                player.addEffect(new net.minecraft.world.effect.MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.WATER_BREATHING, 100, 0, false, false));
                if (player.getAirSupply() < 300) player.setAirSupply(300);
                break;
            case "tinkers_orgen:intestine":
                add(Attributes.MAX_HEALTH, u(B_HEART + 2000), durability * 0.00005f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                player.removeEffect(net.minecraft.world.effect.MobEffects.HUNGER);
                player.removeEffect(net.minecraft.world.effect.MobEffects.POISON);
                player.removeEffect(net.minecraft.world.effect.MobEffects.CONFUSION);
                player.removeEffect(net.minecraft.world.effect.MobEffects.BLINDNESS);
                break;
            case "tinkers_orgen:kidney":
                add(Attributes.ATTACK_SPEED, u(B_SPINE_NERV + 2000), attackSpeed * 0.02f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            case "tinkers_orgen:liver":
                add(Attributes.MAX_HEALTH, u(B_HEART + 4000), durability * 0.00005f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            case "tinkers_orgen:spleen":
                add(Attributes.MAX_HEALTH, u(B_HEART + 5000), durability * 0.00003f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            case "tinkers_orgen:stomach":
                add(Attributes.MAX_HEALTH, u(B_HEART + 6000), durability * 0.00005f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                // 每 tick 恢复饱食度
                var food = player.getFoodData();
                if (food.getFoodLevel() < 20) food.setFoodLevel(Math.min(20, food.getFoodLevel() + 1));
                if (food.getSaturationLevel() < 20) food.setSaturation(Math.min(20f, food.getSaturationLevel() + 0.5f));
                break;
            case "tinkers_orgen:bladder":
                add(Attributes.MAX_HEALTH, u(B_HEART + 7000), durability * 0.00004f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            case "tinkers_orgen:appendix":
                add(Attributes.LUCK, u(B_APPENDIX), (attackDamage + attackSpeed + miningSpeed) * 0.001f, AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
        }
    }

    /** 移除指定器官类型的所有修饰（所有槽位） */
    public static void removeAllAttributes(Player player, String organId) {
        long[] bases = switch (organId) {
            case "tinkers_orgen:heart"    -> new long[]{B_HEART};
            case "tinkers_orgen:lung"     -> new long[]{B_HEART + 1000};
            case "tinkers_orgen:intestine"-> new long[]{B_HEART + 2000};
            case "tinkers_orgen:kidney"   -> new long[]{B_SPINE_NERV + 2000};
            case "tinkers_orgen:liver"    -> new long[]{B_HEART + 4000};
            case "tinkers_orgen:spleen"   -> new long[]{B_HEART + 5000};
            case "tinkers_orgen:stomach"  -> new long[]{B_HEART + 6000};
            case "tinkers_orgen:bladder"  -> new long[]{B_HEART + 7000};
            case "tinkers_orgen:muscle"   -> new long[]{B_MUSCLE_ATK, B_MUSCLE_SPD};
            case "tinkers_orgen:rib"      -> new long[]{B_RIB_DEF, B_RIB_TOUGH};
            case "tinkers_orgen:spine" -> {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
                yield new long[]{B_SPINE_DEF, B_SPINE_NERV, B_SPINE_TOUGH};
            }
            case "tinkers_orgen:appendix" -> new long[]{B_APPENDIX};
            default -> new long[0];
        };
        var list = List.of(Attributes.MAX_HEALTH, Attributes.ATTACK_DAMAGE,
                Attributes.MOVEMENT_SPEED, Attributes.ARMOR,
                Attributes.ATTACK_SPEED, Attributes.LUCK,
                Attributes.ARMOR_TOUGHNESS);
        for (long base : bases) {
            for (var attr : list) {
                var inst = player.getAttribute(attr);
                if (inst == null) continue;
                inst.getModifiers().stream()
                    .filter(m -> (m.getId().getMostSignificantBits() & 0xFFFFFFFF00000000L) == (base & 0xFFFFFFFF00000000L))
                    .toList().forEach(inst::removeModifier);
            }
        }
    }

    private void add(net.minecraft.world.entity.ai.attributes.Attribute attr,
                     UUID uuid, double value, AttributeModifier.Operation op) {
        var inst = player.getAttribute(attr);
        if (inst == null) return;
        inst.getModifiers().stream().filter(m -> m.getId().equals(uuid))
            .toList().forEach(inst::removeModifier);
        inst.addPermanentModifier(new AttributeModifier(uuid, "organ", value, op));
    }
}
