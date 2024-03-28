package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.TOPUtil;
import com.ecaree.botanicprobe.mixin.AccessorSubTileRafflowsia;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.ModSubtiles;
import vazkii.botania.common.block.subtile.generating.SubTileRafflowsia;
import vazkii.botania.common.lib.ModTags;

import java.util.List;
import java.util.ListIterator;

public class Rafflowsia implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("rafflowsia");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileRafflowsia tile) {
            ListTag lastFlowers = tile.getUpdateTag().getList(SubTileRafflowsia.TAG_LAST_FLOWERS, 8);
            final int lastFlowerTimes = tile.getUpdateTag().getInt(SubTileRafflowsia.TAG_LAST_FLOWER_TIMES);
            final int streakLength = tile.getUpdateTag().getInt(SubTileRafflowsia.TAG_STREAK_LENGTH);
            ItemStack itemStack = player.getMainHandItem();
            Block block = Block.byItem(itemStack.getItem());

            if (streakLength != 0 && streakLength != -1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.mana_gained") + streakLength + I18n.get("botanicprobe.text.times"));
            }

            if (block.defaultBlockState().is(ModTags.Blocks.SPECIAL_FLOWERS) && block != ModSubtiles.rafflowsia) {
                iProbeInfo.text(I18n.get("botanicprobe.text.holding_flower") + itemStack.getDisplayName().getString()
                        + " " + I18n.get("botanicprobe.text.will_generate")
                        + manaToGenerate(streakLength, block, tile, lastFlowerTimes, lastFlowers)
//                        + manaToGenerate(streakLength, block, tile)
                        + " Mana");
            }

            if (player.isCrouching()) {
                List<ItemStack> lastFlowersStack = TOPUtil.convertBlockListTagToItemStack(lastFlowers);
                if (!lastFlowersStack.isEmpty()) {
                    iProbeInfo.text(I18n.get("botanicprobe.text.last_flowers"));
                    int rows = 0;
                    int idx = 0;
                    IProbeInfo horizontal = null;
                    for (ItemStack stackInSlot : lastFlowersStack) {
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
                        horizontal.item(stackInSlot);
                        lastFlowersStack.get(0).setCount(lastFlowerTimes);
                        idx++;
                    }
                }
            }
        }
    }

    /**
     * @see <a href="https://github.com/VazkiiMods/Botania/blob/1.18.x/Xplat/src/main/java/vazkii/botania/common/block/subtile/generating/SubTileRafflowsia.java#L54-L82">Botania Source Code</a>
     */

    private int manaToGenerate(int streakLength, Block block, SubTileRafflowsia tile, int lastFlowerTimes, ListTag lastFlowers) {
        streakLength = Math.min(streakLength + 1, processFood(block, TOPUtil.convertBlockListTagToBlock(lastFlowers), tile));
        return getValueForStreak(streakLength, lastFlowerTimes, tile);
    }

    // 不改变 SubTileRafflowsia 实例状态
    private int processFood(Block flower, List<Block> lastFlowers, SubTileRafflowsia tile) {
        for (ListIterator<Block> it = lastFlowers.listIterator(); it.hasNext();) {
            int index = it.nextIndex();
            Block streakFlower = it.next();
            if (streakFlower == flower) {
                it.remove();
                lastFlowers.add(0, streakFlower);
                return index;
            }
        }
        lastFlowers.add(0, flower);
        if (lastFlowers.size() >= ((AccessorSubTileRafflowsia) tile).invokeGetMaxStreak()) {
            lastFlowers.remove(lastFlowers.size() - 1);
        }
        return ((AccessorSubTileRafflowsia) tile).invokeGetMaxStreak();
    }

    // 不改变 SubTileRafflowsia 实例状态
    private int getValueForStreak(int index, int lastFlowerTimes, SubTileRafflowsia tile) {
        if (index != 0) {
            lastFlowerTimes = 0;
        }
        return getStreakOutputs(tile)[index] / ++lastFlowerTimes;
    }

    private int[] getStreakOutputs(SubTileRafflowsia tile) {
        return ((AccessorSubTileRafflowsia) tile).getStreakOutputs();
    }

    /**
     * @deprecated
     * Buggy
     */
    @Deprecated
    private int manaToGenerate(int streakLength, Block block, SubTileRafflowsia tile) {
        streakLength = Math.min(streakLength + 1, ((AccessorSubTileRafflowsia) tile).invokeProcessFlower(block));
        return ((AccessorSubTileRafflowsia) tile).invokeGetValueForStreak(streakLength);
    }
}
