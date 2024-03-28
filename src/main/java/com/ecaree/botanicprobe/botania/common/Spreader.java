package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.mana.TileSpreader;

public class Spreader implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("spreader");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileSpreader tile) {
            final int mana = tile.getCurrentMana();
            final int manaMax = tile.getMaxMana();

            iProbeInfo.text("Mana: " + mana + "/" + manaMax);
        }
    }
}
