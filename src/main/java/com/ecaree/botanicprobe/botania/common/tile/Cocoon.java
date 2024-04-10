package com.ecaree.botanicprobe.botania.common.tile;

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
import vazkii.botania.common.block.tile.TileCocoon;

public class Cocoon implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("cocoon");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileCocoon tile) {
            final int cooldown = TileCocoon.TOTAL_TIME - tile.timePassed;
            final int MAX_EMERALDS = TileCocoon.MAX_EMERALDS;
            final int MAX_CHORUS_FRUITS = TileCocoon.MAX_CHORUS_FRUITS;
            final int emeraldsGiven = tile.emeraldsGiven;
            final int chorusFruitGiven = tile.chorusFruitGiven;
            final boolean gaiaSpiritGiven = tile.gaiaSpiritGiven;
            final String isGaiaSpiritGiven = gaiaSpiritGiven ? I18n.get("botanicprobe.text.yes") : I18n.get("botanicprobe.text.no");
            final String gaiaSpiritGivenStr = I18n.get("botanicprobe.text.gaia_spirit_given") + isGaiaSpiritGiven;
            final String villagerChance = TOPUtil.floatToPercentage(Math.min(1F, (float) emeraldsGiven / (float) MAX_EMERALDS));
            final String shulkerChance = TOPUtil.floatToPercentage(Math.min(1F, (float) chorusFruitGiven / (float) MAX_CHORUS_FRUITS));

            if (cooldown != 1) {
                ContentCollector.addText(TOPUtil.STATUS_STACK,
                        I18n.get("botanicprobe.text.cooldown") + cooldown + " Ticks", gaiaSpiritGivenStr);
            } else {
                ContentCollector.addText(TOPUtil.STATUS_STACK,
                        I18n.get("botanicprobe.text.cooldown") + cooldown + " Tick", gaiaSpiritGivenStr);
            }

            if (emeraldsGiven == 0) {
                ContentCollector.addText(new ItemStack(Items.EMERALD),
                        I18n.get("botanicprobe.text.emeralds_given") + emeraldsGiven);
            } else {
                ContentCollector.addText(new ItemStack(Items.EMERALD),
                        I18n.get("botanicprobe.text.emeralds_given") + emeraldsGiven,
                        I18n.get("botanicprobe.text.villager_chance") + villagerChance);
            }

            if (chorusFruitGiven == 0) {
                ContentCollector.addText(new ItemStack(Items.CHORUS_FRUIT),
                        I18n.get("botanicprobe.text.chorus_fruit_given") + chorusFruitGiven);
            } else {
                ContentCollector.addText(new ItemStack(Items.CHORUS_FRUIT),
                        I18n.get("botanicprobe.text.chorus_fruit_given") + chorusFruitGiven,
                        I18n.get("botanicprobe.text.shulker_chance") + shulkerChance);
            }
        }
    }
}