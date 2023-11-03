package com.pyehouse.custom_menus_and_screens.server;

import com.pyehouse.custom_menus_and_screens.ModEventRegistrar;
import net.minecraftforge.eventbus.api.IEventBus;

public class ServerEventRegistrar extends ModEventRegistrar {
    public ServerEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        super(modEventBus, forgeEventBus);
    }

    @Override
    public void registration() {

    }
}
