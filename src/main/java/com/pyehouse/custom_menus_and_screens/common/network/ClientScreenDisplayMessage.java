package com.pyehouse.custom_menus_and_screens.common.network;

import com.pyehouse.custom_menus_and_screens.ModMain;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraft.network.FriendlyByteBuf;

public class ClientScreenDisplayMessage {
    private boolean messageValid;
    private ScreenDef screenDef;

    public boolean isMessageValid() { return messageValid; }
    public ScreenDef getScreenDef() { return screenDef; }

    public ClientScreenDisplayMessage() {
        messageValid = false;
    }

    public ClientScreenDisplayMessage(ScreenDef screenDef) {
        this.screenDef = screenDef;
        this.messageValid = true;
    }

    public static ClientScreenDisplayMessage decode(FriendlyByteBuf buf) {
        ClientScreenDisplayMessage retval = new ClientScreenDisplayMessage();
        try {
            retval.screenDef = ScreenDef.fromJson(buf.readUtf());

            retval.messageValid = true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            ModMain.logWarn("Exception while reading ClientScreenDisplayMessage: " + e);
        }
        return retval;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!messageValid) return;

        buf.writeUtf(this.screenDef.toJson());
    }
}
