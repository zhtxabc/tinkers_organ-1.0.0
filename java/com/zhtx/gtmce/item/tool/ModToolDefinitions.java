package com.zhtx.gtmce.item.tool;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.resources.ResourceLocation;

import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

/**
 * 工具定义 — 将 Heart 注册为 TiC3 工具。
 * <p>
 * 工具数据完全从 JSON 加载：
 * {@code data/gtmce/tinkering/tool_definitions/heart.json}
 * <p>
 * 不要在 Java 代码中调用 {@code HEART.setData()} — 这会与 JSON 加载器
 * （{@code ToolDefinitionLoader}）冲突，因为 Java 的 {@code PartsModule}
 * 和 JSON 的 {@code tconstruct:part_stats} 是不同的模块类型。
 * JSON 使用 {@code tconstruct:part_stats}（对应 {@code PartStatsModule}），
 * 它同时定义部件列表和属性计算方式，是 TiC3 的标准做法。
 */
public class ModToolDefinitions {

    /** Heart 工具定义，供 {@link ModTools} 引用 */
    public static final ToolDefinition HEART =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "heart"));

    /** 肺工具定义 */
    public static final ToolDefinition LUNG =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "lung"));

    /** 肠工具定义 */
    public static final ToolDefinition INTESTINE =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "intestine"));
    /** 肾工具定义 */
    public static final ToolDefinition KIDNEY =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "kidney"));
    /** 肝工具定义 */
    public static final ToolDefinition LIVER =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "liver"));
    /** 肌肉工具定义 */
    public static final ToolDefinition MUSCLE =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "muscle"));
    /** 肋骨工具定义 */
    public static final ToolDefinition RIB =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "rib"));
    /** 脊椎工具定义 */
    public static final ToolDefinition SPINE =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "spine"));
    /** 脾工具定义 */
    public static final ToolDefinition SPLEEN =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "spleen"));
    /** 胃工具定义 */
    public static final ToolDefinition STOMACH =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "stomach"));
    /** 阑尾工具定义 */
    public static final ToolDefinition APPENDIX =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "appendix"));

    /** 膀胱工具定义 */
    public static final ToolDefinition BLADDER =
            ToolDefinition.create(new ResourceLocation(Gtmce.MOD_ID, "bladder"));

    /**
     * 在 {@code FMLCommonSetupEvent} 中调用。
     * 仅触发 RegistryObject 解析以确保部件在工具之前注册。
     * 工具数据由 {@code ToolDefinitionLoader} 从 JSON 加载。
     */
    public static void init() {
        // 触发 RegistryObject 解析，确保部件在工具引用之前完成注册
        ModToolParts.FLESH.get();
        ModToolParts.HEART_BASE.get();
        ModToolParts.LUNG_BASE.get();
        ModToolParts.INTESTINE_BASE.get();
        ModToolParts.KIDNEY_BASE.get();
        ModToolParts.LIVER_BASE.get();
        ModToolParts.MUSCLE_BASE.get();
        ModToolParts.RIB_BASE.get();
        ModToolParts.SPINE_BASE.get();
        ModToolParts.SPLEEN_BASE.get();
        ModToolParts.STOMACH_BASE.get();
        ModToolParts.APPENDIX_BASE.get();
        ModToolParts.BLADDER_BASE.get();
    }
}
