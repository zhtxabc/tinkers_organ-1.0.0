package com.zhtx.gtmce.item.tool;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

/**
 * 自定义 TiC3 部件 — 器官基底 + 血肉
 */
public class ModToolParts {

    public static final DeferredRegister<Item> PARTS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Gtmce.MOD_ID);

    public static final RegistryObject<ToolPartItem> FLESH = PARTS.register("flesh",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "binding")));

    // 各器官基底
    public static final RegistryObject<ToolPartItem> HEART_BASE = PARTS.register("heart_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> LUNG_BASE = PARTS.register("lung_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> INTESTINE_BASE = PARTS.register("intestine_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> KIDNEY_BASE = PARTS.register("kidney_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> LIVER_BASE = PARTS.register("liver_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> MUSCLE_BASE = PARTS.register("muscle_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> RIB_BASE = PARTS.register("rib_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> SPINE_BASE = PARTS.register("spine_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> SPLEEN_BASE = PARTS.register("spleen_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> STOMACH_BASE = PARTS.register("stomach_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> APPENDIX_BASE = PARTS.register("appendix_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
    public static final RegistryObject<ToolPartItem> BLADDER_BASE = PARTS.register("bladder_base",
            () -> new ToolPartItem(new Item.Properties(), new MaterialStatsId("tconstruct", "head")));
}
