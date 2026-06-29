package com.zhtx.gtmce;

import com.mojang.logging.LogUtils;
import com.zhtx.gtmce.event.ChestCavityOrganEvent;
import com.zhtx.gtmce.item.ModCreativeModeTabs;
import com.zhtx.gtmce.item.ModItems;
import com.zhtx.gtmce.item.tool.ModToolDefinitions;
import com.zhtx.gtmce.item.tool.ModToolParts;
import com.zhtx.gtmce.item.tool.ModTools;
import com.zhtx.gtmce.menu.ModMenuTypes;
import com.zhtx.gtmce.network.ModMessages;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(Gtmce.MOD_ID)
public class Gtmce
{
    public static final String MOD_ID = "tinkers_orgen";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Gtmce()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeModeTabs.CREATIVE_MODE_TAB_DEFERRED_REGISTER.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);
        ModTools.TOOLS.register(modEventBus);
        ModToolParts.PARTS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        ModToolDefinitions.init();
        ModMessages.register();
        LOGGER.info("HELLO FROM COMMON SETUP");
        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        MinecraftForge.EVENT_BUS.post(new ChestCavityOrganEvent());
        LOGGER.info("HELLO from server starting");
    }
}