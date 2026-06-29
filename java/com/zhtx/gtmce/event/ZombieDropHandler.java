package com.zhtx.gtmce.event;

import com.zhtx.gtmce.Gtmce;
import com.zhtx.gtmce.item.tool.ModToolParts;
import com.zhtx.gtmce.menu.FilteredContainer;
import com.zhtx.gtmce.network.PlayerChestStorage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Gtmce.MOD_ID)
public class ZombieDropHandler {

    @SubscribeEvent
    public static void onZombieDrops(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof Zombie)) return;

        DamageSource source = event.getSource();
        if (source == null) return;
        LivingEntity attacker = source.getEntity() instanceof LivingEntity e ? e : null;
        if (!(attacker instanceof Player player)) return;

        var container = PlayerChestStorage.getOrCreate(player);
        boolean hasFabao = false;
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (container.getItem(i).is(FilteredContainer.FABAO_TAG)) {
                hasFabao = true;
                break;
            }
        }
        if (!hasFabao) return;

        event.getDrops().add(event.getEntity().spawnAtLocation(
                new ItemStack(ModToolParts.FLESH.get())));

        OrganDropEvent organEvent = new OrganDropEvent(player, event);
        MinecraftForge.EVENT_BUS.post(organEvent);
        for (ItemStack extra : organEvent.getExtraDrops()) {
            event.getDrops().add(event.getEntity().spawnAtLocation(extra));
        }
    }
}