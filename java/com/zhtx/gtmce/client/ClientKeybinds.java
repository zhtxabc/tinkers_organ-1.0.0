package com.zhtx.gtmce.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

/**
 * 快捷键定义
 */
public class ClientKeybinds {
    public static final String KEY_CATEGORY = "key.categories.tinkers_orgen";
    public static final String KEY_MATERIAL_BOX = "key.tinkers_orgen.material_box";

    public static final KeyMapping MATERIAL_BOX_KEY = new KeyMapping(
            KEY_MATERIAL_BOX,
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            KEY_CATEGORY
    );
}
