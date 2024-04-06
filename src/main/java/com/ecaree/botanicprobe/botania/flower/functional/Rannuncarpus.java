package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.mixin.AccessorSubTileRannuncarpus;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import vazkii.botania.api.item.IFlowerPlaceable;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.common.block.ModSubtiles;
import vazkii.botania.common.block.subtile.functional.SubTileHopperhock;
import vazkii.botania.common.block.subtile.functional.SubTileRannuncarpus;

import java.util.List;

public class Rannuncarpus implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("rannuncarpus");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        BlockPos pos = data.getPos();
        if (level.getBlockEntity(data.getPos()) instanceof SubTileRannuncarpus tile) {
            final int placeRange = tile.getPlaceRange();
            final int verticalPlaceRange = tile.getVerticalPlaceRange();
            RadiusDescriptor secondaryRadius = tile.getSecondaryRadius();
            final boolean stateSensitive = tile.getUpdateTag().getBoolean("stateSensitive");
            final String mode = stateSensitive ? I18n.get("botaniamisc.rannuncarpus.state_sensitive") : I18n.get("botaniamisc.rannuncarpus.state_insensitive");
            ItemStack recieverStack = new ItemStack(tile.getUnderlyingBlock().getBlock());

            ContentCollector.addText(new ItemStack(Items.SPYGLASS),
                    I18n.get("botanicprobe.text.working_range")
                            + (placeRange * 2 + 1) + " x "
                            + (verticalPlaceRange * 2 + 1) + " x "
                            + (placeRange * 2 + 1));

            if (secondaryRadius != null) {
                final int pickupRange = convertRectangleToRadius((RadiusDescriptor.Rectangle) secondaryRadius);

                ContentCollector.addText(new ItemStack(Items.COMPASS),
                        I18n.get("botanicprobe.text.pickup_range")
                                + (pickupRange * 2 + 1) + " x "
                                + (3 * 2 + 1) + " x "   // 硬编码，SubTileRannuncarpus.PICKUP_RANGE_Y = 3
                                + (pickupRange * 2 + 1));
            } else {
                // 当 placeRange 等于 pickupRange 导致 getSecondaryRadius 方法返回 null 的情况
                ContentCollector.addText(new ItemStack(Items.COMPASS),
                        I18n.get("botanicprobe.text.pickup_range")
                                + (placeRange * 2 + 1) + " x "
                                + (3 * 2 + 1) + " x "   // 硬编码，SubTileRannuncarpus.PICKUP_RANGE_Y = 3
                                + (placeRange * 2 + 1));
            }

            ContentCollector.addText(new ItemStack(Items.CLOCK),
                    I18n.get("botanicprobe.text.mode") + mode);

            if (!recieverStack.isEmpty()) {
                ContentCollector.addText(recieverStack,
                        I18n.get("botanicprobe.text.block_for_placement")
                                + recieverStack.getDisplayName().getString()); // 带中括号的本地化名
            }

            if (player.isCrouching()) {
                List<ItemStack> filter = SubTileHopperhock.getFilterForInventory(level, ((AccessorSubTileRannuncarpus) tile).invokeGetFilterPos(), false);

                if (!filter.isEmpty()) {
                    int rows = 0;
                    int idx = 0;
                    for (ItemStack stackInSlot : filter) {
                        if (!stackInSlot.isEmpty()) { // 不显示当物品展示框没有物品的情况
                            if (stackInSlot.getItem() instanceof BlockItem || stackInSlot.getItem() instanceof IFlowerPlaceable) { // 必须是可放置的方块
                                if (idx % 10 == 0) {
                                    ContentCollector.addText(I18n.get("botanicprobe.text.block_to_place"));
                                    rows++;
                                    if (rows > 4) {
                                        break;
                                    }
                                }
                                ContentCollector.addItem(stackInSlot);
                                idx++;
                            }
                        }
                    }
                }
            }

        } else {
            /*
             * 为手掌花指定的用于放置的方块显示其对应的手掌花坐标
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
                            if (blockEntity instanceof SubTileRannuncarpus tile) {
                                Block targetBlock = tile.getUnderlyingBlock().getBlock();
                                if (blockState.getBlock() == targetBlock) {
                                    int placeRange = tile.getPlaceRange();
                                    int verticalPlaceRange = tile.getVerticalPlaceRange();
                                    BlockPos tilePos = tile.getBlockPos();

                                    if (pos.getX() <= tilePos.getX() + placeRange && pos.getX() >= tilePos.getX() - placeRange &&
                                            pos.getY() <= tilePos.getY() + verticalPlaceRange && pos.getY() >= tilePos.getY() - verticalPlaceRange &&
                                            pos.getZ() <= tilePos.getZ() + placeRange && pos.getZ() >= tilePos.getZ() - placeRange) {
                                        TOPUtil.getHorizontal(iProbeInfo)
                                                .item(new ItemStack(ModSubtiles.rannuncarpus), new ItemStyle().width(16).height(16))
                                                .text(I18n.get("botanicprobe.text.binding_rannuncarpus")
                                                        + TOPUtil.getPosString(tilePos));
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

    private static int convertRectangleToRadius(RadiusDescriptor.Rectangle rectangle) {
        AABB aabb = rectangle.aabb();
        int xRadius = (int) ((aabb.maxX - aabb.minX - 1) / 2);
        int zRadius = (int) (aabb.maxZ - aabb.minZ - 1) / 2;

        if (xRadius == zRadius) {
            return xRadius;
        } else {
            return 0;
        }
    }
}