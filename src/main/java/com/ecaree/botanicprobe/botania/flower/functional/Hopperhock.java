package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import vazkii.botania.common.block.subtile.functional.SubTileHopperhock;

import java.util.ArrayList;
import java.util.List;

public class Hopperhock implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("hopperhock");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        BlockPos pos = data.getPos();

        if (level.getBlockEntity(pos) instanceof SubTileHopperhock tile) {
            int range = tile.getRange();
            int filterType = tile.getUpdateTag().getInt("filterType");
            String mode = switch (filterType) {
                case 0 -> I18n.get("botaniamisc.filter0");
                case 1 -> I18n.get("botaniamisc.filter1");
                case 2 -> I18n.get("botaniamisc.filter2");
                default -> "null";
            };

            iProbeInfo.text(I18n.get("botanicprobe.text.range") + range);
            iProbeInfo.text(I18n.get("botanicprobe.text.mode") + mode);

            if (player.isCrouching()) {
                List<ItemStack> items = new ArrayList<>();

                for (Direction dir : Direction.values()) {
                    List<ItemStack> filter = SubTileHopperhock.getFilterForInventory(level, tile.getEffectivePos().relative(dir), true);
                    if (!filter.isEmpty()) {
                        items.addAll(filter);
                    }
                }

                if (!items.isEmpty()) {
                    int rows = 0;
                    int idx = 0;
                    IProbeInfo horizontal = null;
                    for (ItemStack stackInSlot : items) {
                        if (!stackInSlot.isEmpty()) {   // 不显示当物品展示框没有物品的情况
                            if (idx % 10 == 0) {
                                horizontal = iProbeInfo.vertical(iProbeInfo
                                                .defaultLayoutStyle()
                                                .borderColor(TOPUtil.LIGHT_BLUE)
                                                .spacing(0))
                                        .horizontal(new LayoutStyle().spacing(0));
                                rows++;
                                if (rows > 4) {
                                    break;
                                }
                            }
                            iProbeInfo.text(I18n.get("botanicprobe.text.filtered_items"));
                            horizontal.item(stackInSlot);
                            idx++;
                        }
                    }
                }
            }
        }

        // 为漏斗花周围的容器显示对应的过滤的物品
        if (player.isCrouching()) {
            for (BlockPos checkPos : BlockPos.withinManhattan(pos, 1, 0, 1)) {
                if (level.getBlockEntity(checkPos) instanceof SubTileHopperhock) {
                    List<ItemStack> filter = SubTileHopperhock.getFilterForInventory(level, checkPos, true);

                    // https://github.com/VazkiiMods/Botania/blob/1.18.x/Xplat/src/main/java/vazkii/botania/common/block/subtile/functional/SubTileHopperhock.java#L190-L198
                    for (Direction dir : Direction.values()) {
                        AABB aabb = new AABB(pos.relative(dir));
                        List<ItemFrame> frames = level.getEntitiesOfClass(ItemFrame.class, aabb);
                        for (ItemFrame frame : frames) {
                            if (frame.getDirection() == dir) {
                                filter.add(frame.getItem());
                            }
                        }
                    }

                    if (!filter.isEmpty()) {
                        int rows = 0;
                        int idx = 0;
                        IProbeInfo horizontal = null;
                        for (ItemStack stackInSlot : filter) {
                            if (!stackInSlot.isEmpty()) {   // 不显示当物品展示框没有物品的情况
                                if (idx % 10 == 0) {
                                    iProbeInfo.text(I18n.get("botanicprobe.text.filtered_items"));
                                    horizontal = iProbeInfo.vertical(iProbeInfo
                                                    .defaultLayoutStyle()
                                                    .borderColor(TOPUtil.LIGHT_BLUE)
                                                    .spacing(0))
                                            .horizontal(new LayoutStyle().spacing(0));
                                    rows++;
                                    if (rows > 4) {
                                        break;
                                    }
                                }
                                horizontal.item(stackInSlot);
                                idx++;
                            }
                        }
                    }
                }
            }
        }
    }
}