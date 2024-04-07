package com.ecaree.botanicprobe;

import com.ecaree.botanicprobe.util.TOPUtil;
import io.github.noeppi_noeppi.libx.base.tile.TickableBlock;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;
import vazkii.botania.common.block.tile.TileMod;

public class Tile implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("tile");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileMod
                || level.getBlockEntity(data.getPos()) instanceof TileEntitySpecialFlower
                || level.getBlockEntity(data.getPos()) instanceof TickableBlock) {
            TOPUtil.renderContents(iProbeInfo);
        }
    }
}