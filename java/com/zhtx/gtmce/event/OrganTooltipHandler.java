package com.zhtx.gtmce.event;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * 为 gtmce 器官工具显示动态胸腔属性词条。
 * 根据工具的 NBT 材料属性实时计算加成的具体数值。
 */
@Mod.EventBusSubscriber(modid = Gtmce.MOD_ID)
public class OrganTooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        String id = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
        if (!id.startsWith("tinkers_orgen:")) return;

        float atk, dur, spd, min;
        String materialId;
        int tier;

        // 读取材料属性和真实属性
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("tic_materials")) {
            CompoundTag mats = tag.getCompound("tic_materials");
            materialId = mats.getAllKeys().stream().findFirst().map(mats::getString).orElse("unknown");
        } else {
            return; // 没有材料信息，不显示
        }

        // 读真实属性
        float[] stats = readToolStats(stack);
        atk = stats[0]; dur = stats[1]; spd = stats[2]; min = stats[3]; tier = (int) stats[4];

        List<Component> lines = getOrganLines(id, atk, dur, spd, min);
        if (lines.isEmpty()) return;

        event.getToolTip().add(Component.empty());
        event.getToolTip().add(Component.translatable("tooltip.tinkers_orgen.chest_organ")
                .withStyle(ChatFormatting.GOLD));
        for (Component line : lines) {
            event.getToolTip().add(line);
        }
        event.getToolTip().add(Component.translatable("tooltip.tinkers_orgen.material_scale")
                .withStyle(ChatFormatting.DARK_GRAY));
    }

    private static List<Component> getOrganLines(String id, float atk, float dur, float spd, float min) {
        List<Component> lines = new ArrayList<>();
        switch (id) {
            case "tinkers_orgen:heart" ->
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.01f) + "%").withStyle(ChatFormatting.RED));
            case "tinkers_orgen:lung" -> {
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.003f) + "%").withStyle(ChatFormatting.RED));
                lines.add(Component.literal(" 水下呼吸 + 免疫窒息").withStyle(ChatFormatting.AQUA));
            }
            case "tinkers_orgen:intestine" -> {
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.005f) + "%").withStyle(ChatFormatting.RED));
                lines.add(Component.literal(" 免疫饥饿/中毒/反胃/失明").withStyle(ChatFormatting.GREEN));
            }
            case "tinkers_orgen:kidney" ->
                lines.add(Component.literal(" 攻击速度 +" + fmt(spd * 1f) + "%").withStyle(ChatFormatting.YELLOW));
            case "tinkers_orgen:liver" ->
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.005f) + "%").withStyle(ChatFormatting.RED));
            case "tinkers_orgen:muscle" -> {
                lines.add(Component.literal(" 攻击力 +" + fmt(atk * 0.5f) + "%").withStyle(ChatFormatting.RED));
                lines.add(Component.literal(" 移动速度 +" + fmt(spd * 0.3f) + "%").withStyle(ChatFormatting.YELLOW));
            }
            case "tinkers_orgen:rib" -> {
                lines.add(Component.literal(" 护甲 +" + fmt(dur * 0.04f)).withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal(" 盔甲韧性 +" + fmt(dur * 0.04f)).withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal(" 抗性提升 IV").withStyle(ChatFormatting.DARK_GREEN));
            }
            case "tinkers_orgen:spine" -> {
                lines.add(Component.literal(" 护甲 +" + fmt(dur * 0.005f) + "%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal(" 盔甲韧性 +" + fmt(dur * 0.005f) + "%").withStyle(ChatFormatting.GRAY));
                lines.add(Component.literal(" 攻击速度 +" + fmt(spd * 0.08f) + "%").withStyle(ChatFormatting.YELLOW));
                lines.add(Component.literal(" 可飞行").withStyle(ChatFormatting.AQUA));
            }
            case "tinkers_orgen:spleen" ->
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.003f) + "%").withStyle(ChatFormatting.RED));
            case "tinkers_orgen:stomach" -> {
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.005f) + "%").withStyle(ChatFormatting.RED));
                lines.add(Component.literal(" 持续回复饱食度").withStyle(ChatFormatting.GOLD));
            }
            case "tinkers_orgen:appendix" ->
                lines.add(Component.literal(" 幸运 +" + String.format("%.2f", (atk + spd + min) * 0.02f)).withStyle(ChatFormatting.GREEN));
            case "tinkers_orgen:bladder" -> {
                lines.add(Component.literal(" 生命值 +" + fmt(dur * 0.004f) + "%").withStyle(ChatFormatting.RED));
                lines.add(Component.literal(" 防火").withStyle(ChatFormatting.BLUE));
            }
        }
        return lines;
    }

    private static float[] readToolStats(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains("tic_stats")) return new float[]{1, 50, 1, 1, 1};

        CompoundTag stats = tag.getCompound("tic_stats");
        float a = stats.getFloat("tconstruct:attack_damage");
        float d = stats.getFloat("tconstruct:durability");
        float s = stats.getFloat("tconstruct:attack_speed");
        float m = stats.getFloat("tconstruct:mining_speed");
        int t = stats.getInt("tconstruct:harvest_tier");
        return new float[]{a > 0 ? a : 2f, d > 0 ? d : 100f, s > 0 ? s : 1.2f, m > 0 ? m : 2f, Math.max(1, t)};
    }

    private static String fmt(double v) {
        if (v == (int) v) return String.valueOf((int) v);
        return String.format("%.1f", v);
    }

    private static Component pct(double v) {
        return Component.literal("+" + fmt(v * 100) + "%");
    }
}
