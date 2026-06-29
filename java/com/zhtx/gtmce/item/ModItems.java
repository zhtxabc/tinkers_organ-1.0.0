package com.zhtx.gtmce.item;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Gtmce.MOD_ID);

    // 金质铸膜
    public static final RegistryObject<Item> FLESH_GOLD_CAST =
            ITEMS.register("flesh_gold_cast", () -> new Item(new Item.Properties()));

    // Organ-specific gold casts
    public static final RegistryObject<Item> HEART_BASE_GOLD_CAST = ITEMS.register("heart_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LUNG_BASE_GOLD_CAST = ITEMS.register("lung_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INTESTINE_BASE_GOLD_CAST = ITEMS.register("intestine_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> KIDNEY_BASE_GOLD_CAST = ITEMS.register("kidney_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LIVER_BASE_GOLD_CAST = ITEMS.register("liver_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MUSCLE_BASE_GOLD_CAST = ITEMS.register("muscle_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RIB_BASE_GOLD_CAST = ITEMS.register("rib_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPINE_BASE_GOLD_CAST = ITEMS.register("spine_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPLEEN_BASE_GOLD_CAST = ITEMS.register("spleen_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STOMACH_BASE_GOLD_CAST = ITEMS.register("stomach_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> APPENDIX_BASE_GOLD_CAST = ITEMS.register("appendix_base_gold_cast", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLADDER_BASE_GOLD_CAST = ITEMS.register("bladder_base_gold_cast", () -> new Item(new Item.Properties()));
}
