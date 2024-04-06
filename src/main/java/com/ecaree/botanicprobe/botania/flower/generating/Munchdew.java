package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.generating.SubTileMunchdew;

public class Munchdew implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("munchdew");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileMunchdew tile) {
            final int cooldown = tile.getUpdateTag().getInt(SubTileMunchdew.TAG_COOLDOWN);

            if (cooldown != 0 && cooldown != 1) {
                ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                        I18n.get("botanicprobe.text.cooldown") + cooldown + " Ticks");
            } else if (cooldown == 1) {
                ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                        I18n.get("botanicprobe.text.cooldown") + cooldown + " Tick");
            }
        }
    }
}