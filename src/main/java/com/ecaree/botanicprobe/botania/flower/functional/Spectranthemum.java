package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import vazkii.botania.common.block.subtile.functional.SubTileSpectranthemum;

public class Spectranthemum implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("spectranthemum");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileSpectranthemum tile) {
            final int bindX = tile.getUpdateTag().getInt("bindX");
            final int bindY = tile.getUpdateTag().getInt("bindY");
            final int bindZ = tile.getUpdateTag().getInt("bindZ");
            BlockPos blockPos = new BlockPos(bindX, bindY, bindZ);

            if (!level.getBlockState(blockPos).isAir()) {
                iProbeInfo.text(I18n.get("botanicprobe.text.binding_block")
                        + bindX + " " + bindY + " " + bindZ);
            } else {
                iProbeInfo.text(I18n.get("botanicprobe.text.nonbound_block"));
                if (bindY != Integer.MIN_VALUE) {   // 即曾经被绑定过某个方块
                    iProbeInfo.text(I18n.get("botanicprobe.text.last_binding_block")
                            + bindX + " " + bindY + " " + bindZ);
                }
            }

        } else {
            /*
             * 为凌空蓟绑定的方块显示其绑定的凌空蓟坐标
             * 需要改进以减小性能开销
             */
            int range = 1;
            BlockPos playerPos = player.blockPosition();
            ChunkPos playerChunkPos = new ChunkPos(playerPos);
            int startX = playerChunkPos.x - range;
            int startZ = playerChunkPos.z - range;
            int endX = playerChunkPos.x + range;
            int endZ = playerChunkPos.z + range;

            for (int x = startX; x <= endX; x++) {
                for (int z = startZ; z <= endZ; z++) {
                    if (level.hasChunk(x, z)) {
                        LevelChunk chunk = level.getChunk(x, z);
                        for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
                            if (blockEntity instanceof SubTileSpectranthemum tile) {
                                final int bindX = tile.getUpdateTag().getInt("bindX");
                                final int bindY = tile.getUpdateTag().getInt("bindY");
                                final int bindZ = tile.getUpdateTag().getInt("bindZ");
                                BlockPos bindPos = new BlockPos(bindX, bindY, bindZ);

                                if (bindPos.equals(data.getPos())) {
                                    final int flowerX = tile.getBlockPos().getX();
                                    final int flowerY = tile.getBlockPos().getY();
                                    final int flowerZ = tile.getBlockPos().getZ();
                                    BlockPos flowerPos = new BlockPos(flowerX, flowerY, flowerZ);

                                    if (level.getBlockEntity(flowerPos) != null) {
                                        iProbeInfo.text(I18n.get("botanicprobe.text.binding_spectranthemum")
                                                + flowerX + " " + flowerY + " " + flowerZ);
                                    } else {
                                        iProbeInfo.text(I18n.get("botanicprobe.text.last_binding_spectranthemum")
                                                + flowerX + " " + flowerY + " " + flowerZ);
                                        // 不显示未绑定的情况
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}