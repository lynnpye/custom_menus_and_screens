package com.pyehouse.custom_menus_and_screens.common.screendef;

import com.pyehouse.custom_menus_and_screens.ModMain;
import net.minecraft.network.chat.Component;

public class ComponentDef {
    // Required
    // Unique identifier among all ComponentDefs contained by a ScreenDef. Can be duplicated by a
    // ComponentDef defined in a different ScreenDef
    public String id;
    // Required
    // Valid values:
    //      BUTTON
    // Determines what type of widget this is.
    public ModComponentType type;
    // Required
    // The horizontal offset from the anchor. For _LEFT and _MIDDLE anchors, it pushes to the right.
    // For _RIGHT anchors it pushes to the left.
    public int ax;
    // Required
    // The vertical offset from the anchor. For TOP_ and MIDDLE_ anchors, it pushes down.
    // For BOTTOM_ anchors it pushes up.
    public int ay;
    // Required
    // The width of the widget
    public int width;
    // Required
    // The height of the widget
    public int height;
    // Required
    // The command to run on the server in response to interaction with the widget
    public String action;
    // Required or langKey
    // Raw text to display on the widget. This is not localized and will be used as a fallback if langKey is specified
    // but the localization cannot be found.
    public String text;
    // Required or text
    // A translation key to use to present the text of the widget. If localization lookup fails, falls back to text.
    public String langKey;
    // Optional
    // Valid values:
    //      TOP_LEFT, TOP_MIDDLE, TOP_RIGHT
    //      MIDDLE_LEFT, MIDDLE_MIDDLE, MIDDLE_RIGHT
    //      BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT
    // Used to determine what the coordinates are relative to.
    public AnchorOption anchor;
    // Optional
    // Valid values:
    //      PLAYER
    //      SERVER
    // Only the value 'player' currently matters. If this value is set to 'player', the command context will
    // be executed as the player, allowing use of, for example, @s target selectors.
    // If an '@s' is detected in the command string, 'player' context will be assumed.
    public CommandStackOption preferCommandStack;

    public ComponentDef() {

    }

    public boolean isValid() {
        if (id == null || id.isBlank()) {
            ModMain.logError("ComponentDef invalid: missing id or is blank");
            return false;
        }
        if (type == null) {
            ModMain.logError("ComponentDef id[%s] requires type", id);
            return false;
        }
        if (width < 1) {
            ModMain.logError("ComponentDef id[%s] width must be a positive integer", id);
            return false;
        }
        if (height < 1) {
            ModMain.logError("ComponentDef id[%s] height must be a positive integer", id);
            return false;
        }
        if (action == null || action.isBlank()) {
            ModMain.logError("ComponentDef id[%s] requires a non-blank action", id);
            return false;
        }
        if ((text == null || text.isBlank()) && (langKey == null || langKey.isBlank())) {
            ModMain.logError("ComponentDef id[%s] cannot have both text and langKey null or blank");
            return false;
        }

        return true;
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
}
