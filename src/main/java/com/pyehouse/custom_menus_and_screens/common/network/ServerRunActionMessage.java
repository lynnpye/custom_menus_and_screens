package com.pyehouse.custom_menus_and_screens.common.network;

import com.pyehouse.custom_menus_and_screens.ModMain;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class ServerRunActionMessage {
    private boolean messageValid;
    private String screenId;
    private String componentId;
    private UUID playerUUID;

    public boolean isMessageValid() { return messageValid; }
    public String getScreenId() { return screenId; }
    public String getComponentId() { return componentId; }
    public UUID getPlayerUUID() { return playerUUID; }

    public ServerRunActionMessage() {
        messageValid = false;
    }

    public ServerRunActionMessage(String screenId, String componentId, UUID playerUUID) {
        this.screenId = screenId;
        this.componentId = componentId;
        this.playerUUID = playerUUID;
        this.messageValid = true;
    }

    public static ServerRunActionMessage decode(FriendlyByteBuf buf) {
        ServerRunActionMessage retval = new ServerRunActionMessage();
        try {
            retval.screenId = buf.readUtf();
            boolean componentIdIsNull = buf.readBoolean();
            if (!componentIdIsNull) {
                retval.componentId = buf.readUtf();
            }
            retval.playerUUID = buf.readUUID();

            retval.messageValid = true;
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            ModMain.logWarn("Exception while reading ServerRunActionMessage: " + e);
        }
        return  retval;
    }

    public void encode(FriendlyByteBuf buf) {
        if (!messageValid) return;

        buf.writeUtf(screenId);
        buf.writeBoolean(componentId == null);
        if (componentId != null) {
            buf.writeUtf(componentId);
        }
        buf.writeUUID(playerUUID);
    }
}
