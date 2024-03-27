package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileCell;

public class CellBlock implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "cellblock");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileCell tile) {
            final int generation = tile.getGeneration();
            final boolean ticked = tile.getUpdateTag().getBoolean("ticked");

            iProbeInfo.text(I18n.get("botanicprobe.text.cell_generation") + generation);

            if (ticked) {
                final int flowerX = tile.getUpdateTag().getInt("flowerX");
                final int flowerY = tile.getUpdateTag().getInt("flowerY");
                final int flowerZ = tile.getUpdateTag().getInt("flowerZ");
                BlockPos flowerPos = new BlockPos(flowerX, flowerY, flowerZ);
                if (!level.hasNeighborSignal(flowerPos)) {
                    iProbeInfo.text(I18n.get("botanicprobe.text.dandelifeon_not_working"));
                } else {
                    iProbeInfo.text(I18n.get("botanicprobe.text.working"));
                }
            } else {
                iProbeInfo.text(I18n.get("botanicprobe.text.idle"));
            }
        }
    }
}
