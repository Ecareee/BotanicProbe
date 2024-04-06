package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileCell;

public class CellBlock implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("cellblock");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileCell tile) {
            final int generation = tile.getGeneration();
            final boolean ticked = tile.getUpdateTag().getBoolean("ticked");

            ContentCollector.addText(new ItemStack(Items.MEDIUM_AMETHYST_BUD),
                    I18n.get("botanicprobe.text.cell_generation") + generation);

            if (ticked) {
                final int flowerX = tile.getUpdateTag().getInt("flowerX");
                final int flowerY = tile.getUpdateTag().getInt("flowerY");
                final int flowerZ = tile.getUpdateTag().getInt("flowerZ");
                BlockPos flowerPos = new BlockPos(flowerX, flowerY, flowerZ);

                if (level.hasNeighborSignal(flowerPos)) {
                    ContentCollector.addText(new ItemStack(Items.CLOCK),
                            I18n.get("botanicprobe.text.status")
                                    + I18n.get("botanicprobe.text.status_working"));
                } else {
                    ContentCollector.addText(new ItemStack(Items.CLOCK),
                            I18n.get("botanicprobe.text.status")
                                    + I18n.get("botanicprobe.text.status_dandelifeon_not_working"));
                }
            } else {
                ContentCollector.addText(new ItemStack(Items.CLOCK),
                        I18n.get("botanicprobe.text.status")
                                + I18n.get("botanicprobe.text.status_idle"));
            }
        }
    }
}