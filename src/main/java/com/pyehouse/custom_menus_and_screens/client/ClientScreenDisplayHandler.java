package com.pyehouse.custom_menus_and_screens.client;

import com.pyehouse.custom_menus_and_screens.client.gui.ScreenBuilder;
import com.pyehouse.custom_menus_and_screens.common.network.ClientScreenDisplayMessage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientScreenDisplayHandler {
    public static void onScreenDisplay(final ClientScreenDisplayMessage message, final Supplier<NetworkEvent.Context> ctxSupplier) {
        ctxSupplier.get().enqueueWork(() ->
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> ScreenBuilder.displayScreen(message))
        );
        ctxSupplier.get().setPacketHandled(true);
    }
}
