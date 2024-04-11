package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.mixin.AccessorSubTileArcaneRose;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.generating.SubTileArcaneRose;

public class ArcaneRose implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("arcanerose");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileArcaneRose tile) {
            ItemStack itemStack = player.getMainHandItem();

            if ((itemStack.is(Items.ENCHANTED_BOOK) || itemStack.isEnchanted()) && !itemStack.isEmpty()) {
                int xp = ((AccessorSubTileArcaneRose) tile).invokeGetEnchantmentXpValue(itemStack);
                if (xp > 0) {
                    int manaToGenerate = xp * ((AccessorSubTileArcaneRose) tile).getManaPerXpOrb();
                    String text1;
                    String text2;

                    text1 = I18n.get("botanicprobe.text.holding_item")
                            + itemStack.getDisplayName().getString() // 带中括号的本地化名
                            + " " + I18n.get("botanicprobe.text.will_generate")
                            + manaToGenerate + " Mana";
                    /*
                     * 由于附魔书或附魔物品转换成魔力的过程是：
                     * - 附魔书或附魔物品先变成经验球
                     * - 经验球再转换成魔力
                     * 在这个过程中，如果玩家吸收了经验球，玩家的经验就会产生魔力，
                     * 而玩家的每点经验值产出的魔力 (MANA_PER_XP)，
                     * 与经验球的每点经验值产出的魔力 (MANA_PER_XP_ORB) 不同，
                     * 所以造成了误差
                     */
                    text2 = I18n.get("botanicprobe.text.deviation");
                    ContentCollector.addText(new ItemStack(Items.EXPERIENCE_BOTTLE), text1, text2);
                }
            }
        }
    }
}