package com.pyehouse.custom_menus_and_screens.client.gui;

import com.pyehouse.custom_menus_and_screens.ModMessage;
import com.pyehouse.custom_menus_and_screens.common.screendef.AnchorOption;
import com.pyehouse.custom_menus_and_screens.common.screendef.ComponentDef;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;

public class ModScreen extends Screen {

    private final ScreenDef screenDef;

    public ModScreen(ScreenDef screenDef) {
        super(screenDef.getTitleComponentOrDefault());
        this.screenDef = screenDef;
    }

    @Override
    public void onClose() {
        // we can do other stuff
        if (screenDef.closingAction != null && !screenDef.closingAction.isEmpty()) {
            ModMessage.requestServerRunAction(screenDef.id, null, Minecraft.getInstance().player.getUUID());
        }

        // but also...
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return screenDef.pause;
    }

    @Override
    protected void init() {
        super.init();

        this.buildFromScreenDef();
    }

    private void buildFromScreenDef() {
        AnchorOption anchor = screenDef.anchor;
        for (ComponentDef componentDef : screenDef.components) {
            AbstractWidget aw = WidgetBuilder.fromComponentDef(this, screenDef, componentDef);

            if (aw == null) continue;

            // reanchor as needed
            AnchorOption cAnchor = componentDef.anchor;
            if (cAnchor == null) {
                cAnchor = anchor;
            }
            if (cAnchor != null) {
                switch (cAnchor) {
                    case MIDDLE_LEFT:
                    case MIDDLE_MIDDLE:
                    case MIDDLE_RIGHT:
                        aw.y = (this.height / 2) - (componentDef.height / 2) + componentDef.ay;
                        break;
                    case BOTTOM_LEFT:
                    case BOTTOM_MIDDLE:
                    case BOTTOM_RIGHT:
                        aw.y = this.height - componentDef.ay - componentDef.height;
                        break;
                }
                switch (cAnchor) {
                    case TOP_MIDDLE:
                    case MIDDLE_MIDDLE:
                    case BOTTOM_MIDDLE:
                        aw.x = (this.width / 2) - (componentDef.width / 2) + componentDef.ax;
                        break;
                    case TOP_RIGHT:
                    case MIDDLE_RIGHT:
                    case BOTTOM_RIGHT:
                        aw.x = this.width - componentDef.ax - componentDef.width;
                        break;
                }
            }

            this.addRenderableWidget(aw);
        }
    }
}
