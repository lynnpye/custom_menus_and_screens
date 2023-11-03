package com.pyehouse.custom_menus_and_screens.common.network;

import com.pyehouse.custom_menus_and_screens.ModMain;
import com.pyehouse.custom_menus_and_screens.client.ClientScreenDisplayHandler;
import com.pyehouse.custom_menus_and_screens.server.ServerRunActionHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkConfig {
    public static SimpleChannel channel;

    public static final String MESSAGE_PROTOCOL_VERSION = "1";

    public static final byte CLIENT_SCREEN_DISPLAY = 1;
    public static final byte SERVER_RUN_ACTION = 2;

    public static final ResourceLocation channelURL = new ResourceLocation(ModMain.MODID, "networkchannel");

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> registration());
    }

    private static void registration() {
        channel = NetworkRegistry.newSimpleChannel(
                channelURL,
                () -> MESSAGE_PROTOCOL_VERSION,
                MESSAGE_PROTOCOL_VERSION::equals,
                MESSAGE_PROTOCOL_VERSION::equals
        );

        channel.registerMessage(
                CLIENT_SCREEN_DISPLAY,
                ClientScreenDisplayMessage.class,
                ClientScreenDisplayMessage::encode,
                ClientScreenDisplayMessage::decode,
                ClientScreenDisplayHandler::onScreenDisplay,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        channel.registerMessage(
                SERVER_RUN_ACTION,
                ServerRunActionMessage.class,
                ServerRunActionMessage::encode,
                ServerRunActionMessage::decode,
                ServerRunActionHandler::onRunAction,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
