package com.zhtx.gtmce.client;

import com.zhtx.gtmce.Gtmce;
import com.zhtx.gtmce.network.ModMessages;
import com.zhtx.gtmce.network.OpenMaterialBoxPacket;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gtmce.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ClientKeybinds.MATERIAL_BOX_KEY.consumeClick()) {
            Minecraft mc = Minecraft.getInstance();
            ModMessages.INSTANCE.sendToServer(new OpenMaterialBoxPacket());
        }
    }
}
