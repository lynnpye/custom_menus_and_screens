package com.pyehouse.custom_menus_and_screens.client.gui;

import com.pyehouse.custom_menus_and_screens.ModMessage;
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
        String anchor = screenDef.anchor;
        for (ComponentDef componentDef : screenDef.components) {
            AbstractWidget aw = WidgetBuilder.fromComponentDef(this, screenDef, componentDef);

            if (aw == null) continue;

            // reanchor as needed
            String cAnchor = componentDef.anchor;
            if (cAnchor == null) {
                cAnchor = anchor;
            }
            if (cAnchor != null) {
                // should be in form of [TOP|MIDDLE|BOTTOM]_[LEFT|MIDDLE|RIGHT]
                String[] anchors = cAnchor.split("_");

                if (anchors.length == 2) {
                    switch (anchors[0].toUpperCase()) {
                        case "TOP":
                            // do nothing, aw.y is relative to TOP anyway
                            break;
                        case "MIDDLE":
                            aw.y = (this.height / 2) - (componentDef.height / 2) + componentDef.ay;
                            break;
                        case "BOTTOM":
                            aw.y = this.height - componentDef.ay - componentDef.height;
                            break;
                    }
                    switch (anchors[1].toUpperCase()) {
                        case "LEFT":
                            // do nothing, aw.x is relative to LEFT anyway
                            break;
                        case "MIDDLE":
                            aw.x = (this.width / 2) - (componentDef.width / 2) + componentDef.ax;
                            break;
                        case "RIGHT":
                            aw.x = this.width - componentDef.ax - componentDef.width;
                            break;
                    }
                }
            }

            this.addRenderableWidget(aw);
        }
    }
}
