package com.pyehouse.custom_menus_and_screens;

import net.minecraftforge.eventbus.api.IEventBus;

public abstract class ModEventRegistrar {
    protected final IEventBus modEventBus;
    protected final IEventBus forgeEventBus;

    public ModEventRegistrar(IEventBus modEventBus, IEventBus forgeEventBus) {
        this.modEventBus = modEventBus;
        this.forgeEventBus = forgeEventBus;
    }

    public abstract void registration();
}
