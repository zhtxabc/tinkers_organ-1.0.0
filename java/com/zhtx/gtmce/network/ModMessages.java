package com.zhtx.gtmce.network;

import com.zhtx.gtmce.Gtmce;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static final String PROTOCOL = "1";
    private static int id = 0;

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Gtmce.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void register() {
        INSTANCE.registerMessage(id++, OpenMaterialBoxPacket.class,
                OpenMaterialBoxPacket::encode,
                OpenMaterialBoxPacket::decode,
                OpenMaterialBoxPacket::handle);
    }
}
