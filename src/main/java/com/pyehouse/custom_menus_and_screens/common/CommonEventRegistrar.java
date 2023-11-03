package com.pyehouse.custom_menus_and_screens.common;

import com.pyehouse.custom_menus_and_screens.ModEventRegistrar;
import com.pyehouse.custom_menus_and_screens.common.command.ModCommands;
import com.pyehouse.custom_menus_and_screens.common.network.NetworkConfig;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonEventRegistrar extends ModEventRegistrar {
    public CommonEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        super(modEventBus, forgeEventBus);
    }

    @Override
    public void registration() {
        modEventBus.register(NetworkConfig.class);

        forgeEventBus.register(ModCommands.class);
    }
}
