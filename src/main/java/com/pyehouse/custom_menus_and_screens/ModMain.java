package com.pyehouse.custom_menus_and_screens;

import com.pyehouse.custom_menus_and_screens.client.ClientEventRegistrar;
import com.pyehouse.custom_menus_and_screens.common.CommonEventRegistrar;
import com.pyehouse.custom_menus_and_screens.common.Config;
import com.pyehouse.custom_menus_and_screens.server.ServerEventRegistrar;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModMain.MODID)
public class ModMain
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "custom_menus_and_screens";

    public ModMain()
    {
        Config.initConfig();

        initBusAndRegistration();
    }

    private void initBusAndRegistration()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        final CommonEventRegistrar commonEventRegistrar = new CommonEventRegistrar(modEventBus, forgeEventBus);
        commonEventRegistrar.registration();

        final ServerEventRegistrar serverEventRegistrar = new ServerEventRegistrar(modEventBus, forgeEventBus);
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> serverEventRegistrar::registration);

        final ClientEventRegistrar clientEventRegistrar = new ClientEventRegistrar(modEventBus, forgeEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientEventRegistrar::registration);
    }
}
