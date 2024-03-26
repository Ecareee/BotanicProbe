package com.ecaree.botanicprobe;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BotanicProbe.MOD_ID)
public class BotanicProbe {
    public static final String MOD_ID = "botanicprobe";

    public BotanicProbe() {
        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::sendIMC);
    }

    private void sendIMC(FMLCommonSetupEvent event) {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPHandler::new);
    }
}
