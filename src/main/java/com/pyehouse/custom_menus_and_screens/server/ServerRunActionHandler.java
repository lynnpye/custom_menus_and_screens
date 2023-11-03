package com.pyehouse.custom_menus_and_screens.server;

import com.pyehouse.custom_menus_and_screens.common.Config;
import com.pyehouse.custom_menus_and_screens.common.network.ServerRunActionMessage;
import com.pyehouse.custom_menus_and_screens.common.screendef.CommandStackOption;
import com.pyehouse.custom_menus_and_screens.common.screendef.ComponentDef;
import com.pyehouse.custom_menus_and_screens.common.screendef.ScreenDef;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

public class ServerRunActionHandler {
    public static void onRunAction(final ServerRunActionMessage message, final Supplier<NetworkEvent.Context> ctxSupplier) {
        ctxSupplier.get().enqueueWork(() -> {
            ServerRunActionHandler.runAction(message);
        });
        ctxSupplier.get().setPacketHandled(true);
    }

    private static void runAction(ServerRunActionMessage message) {
        String screenId = message.getScreenId();
        if (screenId == null) {
            return;
        }
        ScreenDef screenDef = Config.getScreenDef(screenId);
        if (screenDef == null) {
            return;
        }

        String componentId = message.getComponentId();
        String action = screenDef.closingAction;
        boolean preferCommandStackPlayer = screenDef.preferCommandStack == CommandStackOption.PLAYER;

        if (componentId != null) {
            for (ComponentDef componentDef : screenDef.components) {
                if (!componentId.equals(componentDef.id)) {
                    continue;
                }
                action = componentDef.action;
                preferCommandStackPlayer = componentDef.preferCommandStack == CommandStackOption.PLAYER;
                break;
            }
        }

        if (action == null || action.isEmpty()) {
            return;
        }

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        CommandSourceStack commandSourceStack = null;
        if (preferCommandStackPlayer || action.contains("@s")) {
            Player player = server.getPlayerList().getPlayer(message.getPlayerUUID());
            if (player != null) {
                commandSourceStack = player.createCommandSourceStack().withPermission(4);
            }
        }
        if (commandSourceStack == null) {
            commandSourceStack = server.createCommandSourceStack().withPermission(4);
        }
        server.getCommands().performPrefixedCommand(commandSourceStack, action);
    }
}
