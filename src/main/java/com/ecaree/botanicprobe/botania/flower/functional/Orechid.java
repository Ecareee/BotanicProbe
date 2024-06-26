package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.mixin.AccessorSubTileOrechid;
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
import vazkii.botania.common.block.subtile.functional.SubTileOrechid;

public class Orechid implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("orechid");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileOrechid tile) {
            BlockPos coords = ((AccessorSubTileOrechid) tile).invokeGetCoordsToPut();
            int ticksExisted = tile.ticksExisted;

            if (coords != null) {
                BlockState state = ((AccessorSubTileOrechid) tile).invokeGetOreToPut(coords, level.getBlockState(coords));
                if (state != null) {
                    ItemStack item = new ItemStack(state.getBlock());
                    String name = item.getDisplayName().getString(); // 带中括号的本地化名
                    int cooldown = tile.getDelay() - ticksExisted % tile.getDelay();

                    ContentCollector.addText(item,
                            I18n.get("botanicprobe.text.replacing_block") + " "
                                    + TOPUtil.posToString(coords) + " "
                                    + I18n.get("botanicprobe.text.with")
                                    + name);
                    if (cooldown != 1) {
                        ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                                I18n.get("botanicprobe.text.cooldown") + cooldown + " Ticks");
                    } else {
                        ContentCollector.addText(TOPUtil.COOLDOWN_STACK,
                                I18n.get("botanicprobe.text.cooldown") + cooldown + " Tick");
                    }
                }
            }
        } else {
            /*
             * 为凝矿兰替换的方块显示其替换的矿石
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
                            if (blockEntity instanceof SubTileOrechid tile) {
                                BlockPos coords = ((AccessorSubTileOrechid) tile).invokeGetCoordsToPut();
                                if (coords != null) {
                                    BlockState state = ((AccessorSubTileOrechid) tile).invokeGetOreToPut(coords, level.getBlockState(coords));
                                    if (state != null) {
                                        ItemStack item = new ItemStack(state.getBlock());
                                        String name = item.getDisplayName().getString(); // 带中括号的本地化名

                                        if (coords.equals(data.getPos())) {
                                            TOPUtil.getHorizontal(iProbeInfo)
                                                    .item(item, new ItemStyle().width(16).height(16))
                                                    .text(I18n.get("botanicprobe.text.replacing_block")
                                                            + I18n.get("botanicprobe.text.with")
                                                            + name);
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
    }
}