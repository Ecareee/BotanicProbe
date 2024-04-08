package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import vazkii.botania.api.block.ITileBound;
import vazkii.botania.common.block.ModSubtiles;
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
            String text1;
            String text2 = null;

            if (!level.getBlockState(blockPos).isAir()) {
                ContentCollector.addText(ModSubtiles.spectranthemum,
                        I18n.get("botanicprobe.text.binding_block")
                                + TOPUtil.getPosString(blockPos));
            } else {
                text1 = I18n.get("botanicprobe.text.unbound_block");

                if (!blockPos.equals(ITileBound.UNBOUND_POS)) { // 即曾经被绑定过某个方块
                    text2 = I18n.get("botanicprobe.text.last_binding_block")
                            + TOPUtil.getPosString(blockPos);
                }

                if (text2 == null) {
                    ContentCollector.addText(ModSubtiles.spectranthemum, text1);
                } else {
                    ContentCollector.addText(ModSubtiles.spectranthemum, text1, text2);
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
                                    BlockPos flowerPos = tile.getBlockPos();

                                    if (level.getBlockEntity(flowerPos) != null) {
                                        TOPUtil.getHorizontal(iProbeInfo)
                                                .item(new ItemStack(ModSubtiles.spectranthemum), new ItemStyle().width(16).height(16))
                                                .text(I18n.get("botanicprobe.text.binding_spectranthemum")
                                                        + TOPUtil.getPosString(flowerPos));
                                    } else {
                                        TOPUtil.getHorizontal(iProbeInfo)
                                                .item(new ItemStack(ModSubtiles.spectranthemum), new ItemStyle().width(16).height(16))
                                                .text(I18n.get("botanicprobe.text.last_binding_spectranthemum")
                                                        + TOPUtil.getPosString(flowerPos));
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