package com.zhtx.gtmce.item.tool;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTools {

    public static final DeferredRegister<Item> TOOLS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Gtmce.MOD_ID);

    public static final RegistryObject<HeartTool> HEART =
            TOOLS.register("heart", () -> new HeartTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.HEART
            ));

    public static final RegistryObject<LungTool> LUNG =
            TOOLS.register("lung", () -> new LungTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.LUNG
            ));

    public static final RegistryObject<IntestineTool> INTESTINE =
            TOOLS.register("intestine", () -> new IntestineTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.INTESTINE
            ));
    public static final RegistryObject<KidneyTool> KIDNEY =
            TOOLS.register("kidney", () -> new KidneyTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.KIDNEY
            ));
    public static final RegistryObject<LiverTool> LIVER =
            TOOLS.register("liver", () -> new LiverTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.LIVER
            ));
    public static final RegistryObject<MuscleTool> MUSCLE =
            TOOLS.register("muscle", () -> new MuscleTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.MUSCLE
            ));
    public static final RegistryObject<RibTool> RIB =
            TOOLS.register("rib", () -> new RibTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.RIB
            ));
    public static final RegistryObject<SpineTool> SPINE =
            TOOLS.register("spine", () -> new SpineTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.SPINE
            ));
    public static final RegistryObject<SpleenTool> SPLEEN =
            TOOLS.register("spleen", () -> new SpleenTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.SPLEEN
            ));
    public static final RegistryObject<StomachTool> STOMACH =
            TOOLS.register("stomach", () -> new StomachTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.STOMACH
            ));
    public static final RegistryObject<AppendixTool> APPENDIX =
            TOOLS.register("appendix", () -> new AppendixTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.APPENDIX
            ));

    public static final RegistryObject<BladderTool> BLADDER =
            TOOLS.register("bladder", () -> new BladderTool(
                    new Item.Properties().stacksTo(1),
                    ModToolDefinitions.BLADDER
            ));
}
