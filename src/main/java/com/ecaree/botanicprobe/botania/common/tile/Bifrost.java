package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileBifrost;

public class Bifrost implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("bifrost");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileBifrost tile) {
            final int ticks = tile.ticks;

            if (ticks != 0 && ticks != 1) {
                ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                        I18n.get("botanicprobe.text.vanishing_time") + ticks + " Ticks");
            } else if (ticks == 1) {
                ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                        I18n.get("botanicprobe.text.vanishing_time") + ticks + " Tick");
            }
        }
    }
}