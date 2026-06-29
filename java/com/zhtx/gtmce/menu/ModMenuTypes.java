package com.zhtx.gtmce.menu;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Gtmce.MOD_ID);

    public static final RegistryObject<MenuType<MaterialBoxMenu>> MATERIAL_BOX_MENU =
            MENU_TYPES.register("material_box",
                    () -> IForgeMenuType.create(MaterialBoxMenu::new));
}
