package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.TOPUtil;
import com.ecaree.botanicprobe.mixin.AccessorSubTileGourmaryllis;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.generating.SubTileGourmaryllis;

import java.util.List;
import java.util.ListIterator;

public class Gourmaryllis implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("gourmaryllis");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileGourmaryllis tile) {
            final int cooldown = tile.getUpdateTag().getInt("cooldown");
            final int digestingMana = tile.getUpdateTag().getInt("digestingMana");
            ListTag lastFoods = tile.getUpdateTag().getList("lastFoods", 10);
            final int lastFoodCount = tile.getUpdateTag().getInt("lastFoodCount");
            final int streakLength = tile.getUpdateTag().getInt("streakLength");
            ItemStack itemStack = player.getMainHandItem();

            if (digestingMana != 0) {
                iProbeInfo.text(I18n.get("botanicprobe.text.generating") + digestingMana + " Mana");
            }
            if (streakLength != 0 && streakLength != -1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.mana_gained") + streakLength + I18n.get("botanicprobe.text.times"));
            }
            if (cooldown != -1 && cooldown != 1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.cooldown") + cooldown + " Ticks");
            } else if (cooldown == 1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.cooldown") + cooldown + " Tick");
            }
            if (itemStack.getItem().isEdible()) {
                iProbeInfo.text(I18n.get("botanicprobe.text.holding_item") + itemStack.getDisplayName().getString()
                        + " " + I18n.get("botanicprobe.text.will_generate")
                        + manaToGenerate(streakLength, itemStack, tile, lastFoodCount, lastFoods)
//                        + manaToGenerate(streakLength, itemStack, tile)
                        + " Mana");
            }

            if (player.isCrouching()) {
                List<ItemStack> lastFoodsStack = TOPUtil.convertListTagToItemStack(lastFoods);
                if (!lastFoodsStack.isEmpty()) {
                    iProbeInfo.text(I18n.get("botanicprobe.text.last_foods"));
                    int rows = 0;
                    int idx = 0;
                    IProbeInfo horizontal = null;
                    for (ItemStack stackInSlot : lastFoodsStack) {
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
                        lastFoodsStack.get(0).setCount(lastFoodCount);
                        idx++;
                    }
                }
            }
        }
    }

    /**
     * @see <a href="https://github.com/VazkiiMods/Botania/blob/1.18.x/Xplat/src/main/java/vazkii/botania/common/block/subtile/generating/SubTileGourmaryllis.java#L58-L90">Botania Source Code</a>
     */

    private int manaToGenerate(int streakLength, ItemStack stack, SubTileGourmaryllis tile, int lastFoodCount, ListTag lastFoods) {
        streakLength = Math.min(streakLength + 1, processFood(stack, TOPUtil.convertListTagToItemStack(lastFoods), tile));
        int val = Math.min(12, stack.getItem().getFoodProperties().getNutrition());
        int digestingMana = val * val * 70;
        double multiplier = getMultiplierForStreak(streakLength, lastFoodCount, tile);
        digestingMana *= multiplier;
        /*
          https://github.com/VazkiiMods/Botania/pull/4452/files#diff-ba0b22a9bad1f1c839789f14902737aef981a407f4c903aff823732765651f21R156
          ???
         */
//        digestingMana *= (int) ((AccessorSubTileGourmaryllis) tile).callGetMultiplierForStreak(streakLength);
        return digestingMana;
    }

    // 不改变 SubTileGourmaryllis 实例状态
    private int processFood(ItemStack food, List<ItemStack> lastFoods, SubTileGourmaryllis tile) {
        for (ListIterator<ItemStack> it = lastFoods.listIterator(); it.hasNext();) {
            int index = it.nextIndex();
            ItemStack streakFood = it.next();
            if (streakFood.sameItem(food) && ItemStack.tagMatches(streakFood, food)) {
                it.remove();
                lastFoods.add(0, streakFood);
                return index;
            }
        }
        ItemStack newestFood = food.copy();
        newestFood.setCount(1);
        lastFoods.add(0, newestFood);
        if (lastFoods.size() >= ((AccessorSubTileGourmaryllis) tile).invokeGetMaxStreak()) {
            lastFoods.remove(lastFoods.size() - 1);
        }
        return ((AccessorSubTileGourmaryllis) tile).invokeGetMaxStreak();
    }

    // 不改变 SubTileGourmaryllis 实例状态
    private double getMultiplierForStreak(int index, int lastFoodCount, SubTileGourmaryllis tile) {
        if (index == 0) {
            return 1.0 / ++lastFoodCount;
        } else {
            lastFoodCount = 1;
            return getStreakMultipliers(tile)[index];
        }
    }

    private double[] getStreakMultipliers(SubTileGourmaryllis tile) {
        return ((AccessorSubTileGourmaryllis) tile).getStreakMultipliers();
    }

    /**
     * @deprecated
     * Buggy
     */
    @Deprecated
    private int manaToGenerate(int streakLength, ItemStack stack, SubTileGourmaryllis tile) {
        streakLength = Math.min(streakLength + 1, ((AccessorSubTileGourmaryllis) tile).invokeProcessFood(stack));
        int val = Math.min(12, stack.getItem().getFoodProperties().getNutrition());
        int digestingMana = val * val * 70;
        digestingMana *= ((AccessorSubTileGourmaryllis) tile).invokeGetMultiplierForStreak(streakLength);
        return digestingMana;
    }
}