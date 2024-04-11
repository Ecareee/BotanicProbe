package com.ecaree.botanicprobe.botania.common.tile.string;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.string.TileRedString;

public class RedString implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("redstring");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileRedString tile) {
            final BlockPos pos = tile.getBinding();
            final Block block = tile.getBlockAtBinding();

            if (pos != null) {
                ContentCollector.addText(block,
                        I18n.get("botanicprobe.text.binding") + TOPUtil.posToString(pos));
            }
        }
    }
}