package com.pyehouse.custom_menus_and_screens.client.gui;

import com.pyehouse.custom_menus_and_screens.common.screendef.ComponentDef;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraft.client.gui.components.AbstractWidget;

public class WidgetBuilder {
    public static AbstractWidget fromComponentDef(ModScreen modScreen, ScreenDef screenDef, ComponentDef componentDef) {
        if (componentDef == null || componentDef.id == null) {
            return null;
        }

        switch (componentDef.type) {
            case "button": {
                return new ModButton(
                        modScreen,
                        screenDef,
                        componentDef
                );
            }
        }

        return null;
    }
}
