package com.zhtx.gtmce.item.tool;

import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;

/**
 * Heart 工具物品 — 由 organ_base（头部）+ flesh（绑定）两个部件组装而成
 */
public class HeartTool extends ModifiableItem {

    /**
     * @param properties     物品属性（堆叠数等），由 {@link ModTools} 传入
     * @param toolDefinition 工具定义（部件槽位、基础属性），来自 {@link ModToolDefinitions#HEART}
     */
    public HeartTool(Properties properties, ToolDefinition toolDefinition) {
        super(properties, toolDefinition);
    }
}