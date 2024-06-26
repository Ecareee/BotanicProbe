package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.generating.SubTileEndoflame;

public class Endoflame implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("endoflame");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileEndoflame tile) {
            final int burnTime = tile.getUpdateTag().getInt("burnTime");

            if (burnTime != 0 && burnTime != 1) {
                ContentCollector.addText(new ItemStack(Items.BLAZE_POWDER),
                        I18n.get("botanicprobe.text.burn_time") + burnTime + " Ticks");
            } else if (burnTime == 1) {
                ContentCollector.addText(new ItemStack(Items.BLAZE_POWDER),
                        I18n.get("botanicprobe.text.burn_time") + burnTime + " Tick");
            }
        }
    }
}