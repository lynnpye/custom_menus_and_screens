package com.pyehouse.custom_menus_and_screens;

import com.pyehouse.custom_menus_and_screens.common.network.ClientScreenDisplayMessage;
import com.pyehouse.custom_menus_and_screens.common.network.NetworkConfig;
import com.pyehouse.custom_menus_and_screens.common.network.ServerRunActionMessage;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ModMessage {
    public static void requestClientScreenDisplay(ScreenDef screenDef, @Nonnull Player player) {
        ClientScreenDisplayMessage message = new ClientScreenDisplayMessage(screenDef);
        NetworkConfig.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }

    public static void requestServerRunAction(String screenId, String componentId, UUID playerUUID) {
        ServerRunActionMessage message = new ServerRunActionMessage(screenId, componentId, playerUUID);
        NetworkConfig.channel.send(PacketDistributor.SERVER.noArg(), message);
    }
}
