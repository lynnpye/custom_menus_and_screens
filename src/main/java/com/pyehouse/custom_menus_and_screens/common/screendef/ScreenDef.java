package com.pyehouse.custom_menus_and_screens.common.screendef;

import com.google.gson.Gson;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

import static com.pyehouse.custom_menus_and_screens.ModMain.MODID;

public class ScreenDef {
    public String id;
    public boolean pause;
    public String title;
    public String anchor;
    public String forceCommandStack;
    public String closingAction;
    public boolean closeOnInteract;
    public List<ComponentDef> components = new ArrayList<>();

    public ScreenDef() {
    }

    public String toJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

    public static ScreenDef fromJson(String json) {
        return new Gson().fromJson(json, ScreenDef.class);
    }

    public Component getTitleComponentOrDefault() {
        return Component.literal(title == null || title.isEmpty() ? MODID : title);
    }

    public boolean isForceCommandStackPlayer() {
        return "player".equalsIgnoreCase(forceCommandStack);
    }
}
