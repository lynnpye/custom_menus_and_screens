package com.pyehouse.custom_menus_and_screens.client.gui;

import com.pyehouse.custom_menus_and_screens.ModMessage;
import com.pyehouse.custom_menus_and_screens.common.screendef.ComponentDef;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class ModButton extends ExtendedButton {
    public ModButton(ModScreen modScreen, ScreenDef screenDef, ComponentDef componentDef) {
        super(componentDef.ax, componentDef.ay, componentDef.width, componentDef.height, componentDef.getDisplayStringComponent(), self -> {
            ModMessage.requestServerRunAction(screenDef.id, componentDef.id, Minecraft.getInstance().player.getUUID());
            if (screenDef.closeOnInteract) {
                modScreen.onClose();
            }
        });
    }
}
