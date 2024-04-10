package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileIncensePlate;

public class IncensePlate implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("incenseplate");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileIncensePlate tile) {
            final int timeLeft = tile.getUpdateTag().getInt("timeLeft");
            final boolean burning = tile.burning;
            final String isBurning = burning ? I18n.get("botanicprobe.text.yes") : I18n.get("botanicprobe.text.no");
            final String burningStr = I18n.get("botanicprobe.text.burning") + isBurning;

            if (burning) {
                if (timeLeft != 0 && timeLeft != 1) {
                    ContentCollector.addText(TOPUtil.STATUS_STACK, burningStr,
                            I18n.get("botanicprobe.text.burn_time") + timeLeft + " Ticks");
                } else if (timeLeft == 1) {
                    ContentCollector.addText(TOPUtil.STATUS_STACK, burningStr,
                            I18n.get("botanicprobe.text.burn_time") + timeLeft + " Tick");
                }
            } else {
                ContentCollector.addText(TOPUtil.STATUS_STACK, burningStr);
            }
        }
    }
}