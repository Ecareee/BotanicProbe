package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.TOPUtil;
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
            final int cooldown = tile.getUpdateTag().getInt("cooldown");

            if (cooldown != 0 && cooldown != 1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.cooldown") + cooldown + " Ticks");
            } else if (cooldown == 1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.cooldown") + cooldown + " Tick");
            }
        }
    }
}
