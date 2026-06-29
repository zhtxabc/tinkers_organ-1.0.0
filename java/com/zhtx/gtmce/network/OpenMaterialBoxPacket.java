package com.zhtx.gtmce.network;

import com.zhtx.gtmce.menu.MaterialBoxMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record OpenMaterialBoxPacket() {

    public static void encode(OpenMaterialBoxPacket msg, FriendlyByteBuf buf) {}

    public static OpenMaterialBoxPacket decode(FriendlyByteBuf buf) {
        return new OpenMaterialBoxPacket();
    }

    public static void handle(OpenMaterialBoxPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            var container = PlayerChestStorage.getOrCreate(player);

            player.openMenu(new net.minecraft.world.SimpleMenuProvider(
                    (id, inv, p) -> new MaterialBoxMenu(id, inv, container),
                    Component.translatable("screen.tinkers_orgen.material_box")
            ));
        });
        ctx.get().setPacketHandled(true);
    }
}
