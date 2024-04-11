package com.ecaree.botanicprobe.botania.flower;

import com.ecaree.botanicprobe.mixin.AccessorSubTilePureDaisy;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.recipe.IPureDaisyRecipe;
import vazkii.botania.common.block.subtile.SubTilePureDaisy;

public class PureDaisy implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("puredaisy");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        BlockPos pos = data.getPos();
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof SubTilePureDaisy tile) {
            for (int i = 0; i < 8; i++) {
                int ticksRemaining = tile.getUpdateTag().getInt("ticksRemaining" + i);

                if (ticksRemaining == -1) {
                    continue;
                }

                IPureDaisyRecipe recipe = ((AccessorSubTilePureDaisy) tile).invokeFindRecipe(pos.offset(getPositions(tile)[i]));

                if (recipe != null) {
                    int requiredTicks = recipe.getTime();
                    int progress = requiredTicks - ticksRemaining;

                    ContentCollector.addProgressBar(progress, requiredTicks);
                }
            }
        }

        int minTicksRemaining = Integer.MAX_VALUE;
        IPureDaisyRecipe fastestRecipe = null;

        for (BlockPos checkPos : BlockPos.withinManhattan(pos, 1, 0, 1)) {
            if (level.getBlockEntity(checkPos) instanceof SubTilePureDaisy tile) {
                for (int i = 0; i < 8; i++) {
                    BlockPos relativePos = tile.getEffectivePos().offset(getPositions(tile)[i]);

                    if (relativePos.equals(pos)) {
                        int ticksRemaining = tile.getUpdateTag().getInt("ticksRemaining" + i);

                        if (ticksRemaining == -1) {
                            continue;
                        }

                        IPureDaisyRecipe recipe = ((AccessorSubTilePureDaisy) tile).invokeFindRecipe(relativePos);

                        if (recipe != null && ticksRemaining < minTicksRemaining) {
                            minTicksRemaining = ticksRemaining;
                            fastestRecipe = recipe;
                        }
                    }
                }
            }
        }

        if (minTicksRemaining != Integer.MAX_VALUE) { // 检查 minTicksRemaining 是否被有效的实例更新过
            int requiredTicks = fastestRecipe.getTime();
            int progress = requiredTicks - minTicksRemaining;

            TOPUtil.setProgressBar(TOPUtil.getHorizontal(iProbeInfo), progress, requiredTicks);
        }
    }

    private BlockPos[] getPositions(SubTilePureDaisy tile) {
        return ((AccessorSubTilePureDaisy) tile).getPositions();
    }
}