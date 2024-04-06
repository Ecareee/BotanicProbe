package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.mixin.AccessorSubTileRafflowsia;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.ModSubtiles;
import vazkii.botania.common.block.subtile.generating.SubTileRafflowsia;
import vazkii.botania.common.lib.ModTags;

import java.util.ArrayList;
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
                ContentCollector.addText(new ResourceLocation("minecraft:textures/mob_effect/jump_boost.png"),
                        I18n.get("botanicprobe.text.mana_gained") + streakLength + I18n.get("botanicprobe.text.times"));
            }

            if (block.defaultBlockState().is(ModTags.Blocks.SPECIAL_FLOWERS) && block != ModSubtiles.rafflowsia) {
                ItemStack displayStack = new ItemStack(itemStack.getItem());
                displayStack.setCount(1);

                ContentCollector.addText(displayStack,
                        I18n.get("botanicprobe.text.holding_flower")
                                + itemStack.getDisplayName().getString() // 带中括号的本地化名
                                + " " + I18n.get("botanicprobe.text.will_generate")
                                + manaToGenerate(streakLength, block, tile, lastFlowerTimes, lastFlowers)
//                        + manaToGenerate(streakLength, block, tile)
                                + " Mana");
            }

            if (player.isCrouching()) {
                List<ItemStack> lastFlowersStack = convertBlockListTagToItemStack(lastFlowers);
                if (!lastFlowersStack.isEmpty()) {
                    int rows = 0;
                    int idx = 0;
                    for (ItemStack stackInSlot : lastFlowersStack) {
                        if (idx % 10 == 0) {
                            ContentCollector.addText(I18n.get("botanicprobe.text.last_flowers"));
                            rows++;
                            if (rows > 4) {
                                break;
                            }
                        }
                        ContentCollector.addItem(stackInSlot);
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
        streakLength = Math.min(streakLength + 1, processFood(block, convertBlockListTagToBlock(lastFlowers), tile));
        return getValueForStreak(streakLength, lastFlowerTimes, tile);
    }

    // 不会改变 SubTileRafflowsia 实例状态
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

    // 不会改变 SubTileRafflowsia 实例状态
    private int getValueForStreak(int index, int lastFlowerTimes, SubTileRafflowsia tile) {
        if (index != 0) {
            lastFlowerTimes = 0;
        }
        return getStreakOutputs(tile)[index] / ++lastFlowerTimes;
    }

    private int[] getStreakOutputs(SubTileRafflowsia tile) {
        return ((AccessorSubTileRafflowsia) tile).getStreakOutputs();
    }

    // 将存储了 Block 类型注册名的 ListTag 类型转换为 ItemStack 类型
    private static List<ItemStack> convertBlockListTagToItemStack(ListTag listTag) {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < listTag.size(); i++) {
            String blockName = listTag.getString(i);
            Block block = Registry.BLOCK.get(new ResourceLocation(blockName));
            if (block != Blocks.AIR) {
                items.add(new ItemStack(block.asItem()));
            }
        }

        return items;
    }

    // 将存储了 Block 类型注册名的 ListTag 类型转换为 Block 类型
    private static List<Block> convertBlockListTagToBlock(ListTag listTag) {
        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < listTag.size(); i++) {
            String blockName = listTag.getString(i);
            Block block = Registry.BLOCK.get(new ResourceLocation(blockName));
            if (block != Blocks.AIR) {
                blocks.add(block);
            }
        }

        return blocks;
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