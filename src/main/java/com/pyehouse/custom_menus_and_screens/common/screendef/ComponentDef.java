package com.pyehouse.custom_menus_and_screens.common.screendef;

import net.minecraft.network.chat.Component;

public class ComponentDef {
    public String id;
    public String type;
    public int ax;
    public int ay;
    public int width;
    public int height;
    public String anchor;
    public String text;
    public String langKey;
    public String forceCommandStack;
    public String action;

    public ComponentDef() {

    }

    public Component getDisplayStringComponent() {
        Component result = null;
        if (langKey != null) {
            result = Component.translatable(langKey);
            if (langKey.equals(result.getString()) && text != null) {
                result = null;
            }
        }
        if (result == null) {
            result = Component.literal(text == null ? "" : text);
        }
        return result;
    }

    public boolean isForceCommandStackPlayer() {
        return "player".equalsIgnoreCase(forceCommandStack);
    }
}
