package com.pyehouse.custom_menus_and_screens.client.gui;

import com.pyehouse.custom_menus_and_screens.common.network.ClientScreenDisplayMessage;
import net.minecraft.client.Minecraft;

public class ScreenBuilder {
    public static void displayScreen(ClientScreenDisplayMessage message){
        Minecraft.getInstance().setScreen(new ModScreen(message.getScreenDef()));
    }
}
