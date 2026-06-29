package com.zhtx.gtmce.item;

import com.zhtx.gtmce.Gtmce;
import com.zhtx.gtmce.item.tool.ModToolParts;
import com.zhtx.gtmce.item.tool.ModTools;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

import java.util.Collection;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB_DEFERRED_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gtmce.MOD_ID);

    public static final RegistryObject<CreativeModeTab> GTMCE_TAB =
            CREATIVE_MODE_TAB_DEFERRED_REGISTER.register("tinkers_orgen_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModToolParts.FLESH.get()))
                    .title(Component.translatable("itemGroup.tinkers_orgen_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModTools.HEART.get());
                        output.accept(ModTools.LUNG.get());
                        output.accept(ModTools.INTESTINE.get());
                        output.accept(ModTools.KIDNEY.get());
                        output.accept(ModTools.LIVER.get());
                        output.accept(ModTools.MUSCLE.get());
                        output.accept(ModTools.RIB.get());
                        output.accept(ModTools.SPINE.get());
                        output.accept(ModTools.SPLEEN.get());
                        output.accept(ModTools.STOMACH.get());
                        output.accept(ModTools.APPENDIX.get());
                        output.accept(ModTools.BLADDER.get());
                        output.accept(ModToolParts.FLESH.get());
                        accept(output, ModToolParts.HEART_BASE.get());
                        accept(output, ModToolParts.LUNG_BASE.get());
                        accept(output, ModToolParts.INTESTINE_BASE.get());
                        accept(output, ModToolParts.KIDNEY_BASE.get());
                        accept(output, ModToolParts.LIVER_BASE.get());
                        accept(output, ModToolParts.MUSCLE_BASE.get());
                        accept(output, ModToolParts.RIB_BASE.get());
                        accept(output, ModToolParts.SPINE_BASE.get());
                        accept(output, ModToolParts.SPLEEN_BASE.get());
                        accept(output, ModToolParts.STOMACH_BASE.get());
                        accept(output, ModToolParts.APPENDIX_BASE.get());
                        accept(output, ModToolParts.BLADDER_BASE.get());
                        output.accept(ModToolParts.FLESH.get()); // 添加方块物品
                        output.accept(ModItems.FLESH_GOLD_CAST.get());
                        output.accept(ModItems.HEART_BASE_GOLD_CAST.get());
                        output.accept(ModItems.LUNG_BASE_GOLD_CAST.get());
                        output.accept(ModItems.INTESTINE_BASE_GOLD_CAST.get());
                        output.accept(ModItems.KIDNEY_BASE_GOLD_CAST.get());
                        output.accept(ModItems.LIVER_BASE_GOLD_CAST.get());
                        output.accept(ModItems.MUSCLE_BASE_GOLD_CAST.get());
                        output.accept(ModItems.RIB_BASE_GOLD_CAST.get());
                        output.accept(ModItems.SPINE_BASE_GOLD_CAST.get());
                        output.accept(ModItems.SPLEEN_BASE_GOLD_CAST.get());
                        output.accept(ModItems.STOMACH_BASE_GOLD_CAST.get());
                        output.accept(ModItems.APPENDIX_BASE_GOLD_CAST.get());
                        output.accept(ModItems.BLADDER_BASE_GOLD_CAST.get());
                    })
                    .build()
            );

    /** 为每个材料生成一个部件堆叠 */
    @SuppressWarnings("deprecation")
    private static void accept(CreativeModeTab.Output output, Item item) {
        if (item instanceof ToolPartItem part) {
            try {
                Class<?> matReg = Class.forName("slimeknights.tconstruct.library.materials.definition.MaterialVariantId");
                Class<?> matInst = Class.forName("slimeknights.tconstruct.library.materials.definition.MaterialId");
                Class<?> matMgr = Class.forName("slimeknights.tconstruct.library.materials.MaterialRegistry");
                Object allMats = matMgr.getMethod("getMaterials").invoke(null);
                @SuppressWarnings("unchecked")
                Collection<Object> mats = (Collection<Object>) allMats;
                var statType = part.getStatType();
                for (Object mat : mats) {
                    try {
                        Object stats = mat.getClass().getMethod("getStat",
                            Class.forName("slimeknights.tconstruct.library.materials.stats.MaterialStatsId"))
                            .invoke(mat, statType);
                        if (stats != null) {
                            Object variantId = mat.getClass().getMethod("getIdentifier").invoke(mat);
                            ItemStack stack = part.withMaterialForDisplay(
                                slimeknights.tconstruct.library.materials.definition.MaterialVariantId.class.cast(variantId));
                            if (!stack.isEmpty()) output.accept(stack);
                        }
                    } catch (Exception ignored) {}
                }
                return;
            } catch (Exception ignored) {}
        }
        output.accept(item);
    }
}