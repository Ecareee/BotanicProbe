package com.ecaree.botanicprobe.botania.common.tile.mana;

import com.ecaree.botanicprobe.mixin.AccessorTileSpreader;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.ILensEffect;
import vazkii.botania.common.block.mana.BlockSpreader;
import vazkii.botania.common.block.tile.mana.TileSpreader;
import vazkii.botania.common.entity.EntityManaBurst;

import java.util.Arrays;

public class Spreader implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("spreader");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileSpreader tile) {
            final BlockPos bindingPos = tile.getBinding();
            final int mana = tile.getCurrentMana();
            final int manaMax = tile.getMaxMana();
            String text1;
            String text2 = "Mana: " + mana + "/" + manaMax;

            if (bindingPos != null) {
                text1 = I18n.get("botanicprobe.text.binding") + TOPUtil.posToString(bindingPos);
            } else {
                text1 = I18n.get("botanicprobe.text.unbound");
            }

            EntityManaBurst burst = ((AccessorTileSpreader) tile).invokeGetBurst(true);
            ItemStack lens = burst.getSourceLens();
            BlockSpreader.Variant variant = tile.getVariant();
            int originalBurstMana = variant.burstMana;
            int originalTicksBeforeManaLoss = variant.preLossTicks;
            float originalManaLossPerTick = variant.lossPerTick;
            float originalMotionModifier = variant.motionModifier;
            int currentBurstMana;
            int currentTicksBeforeManaLoss;
            float currentManaLossPerTick;
            float currentMotionModifier;
            if (!lens.isEmpty()) {
                BurstProperties props = new BurstProperties(originalBurstMana, originalTicksBeforeManaLoss, originalManaLossPerTick, 0F, originalMotionModifier, variant.color);
                ILensEffect lensEffect = (ILensEffect) lens.getItem();
                lensEffect.apply(lens, props, level);
                currentBurstMana = props.maxMana;
                currentTicksBeforeManaLoss = props.ticksBeforeManaLoss;
                currentManaLossPerTick = props.manaLossPerTick;
                currentMotionModifier = props.motionModifier;
            } else {
                currentBurstMana = originalBurstMana;
                currentTicksBeforeManaLoss = originalTicksBeforeManaLoss;
                currentManaLossPerTick = originalManaLossPerTick;
                currentMotionModifier = originalMotionModifier;
            }

            String text3 = getTextWithCheckingValue(I18n.get("botanicprobe.text.burst_mana"), currentBurstMana, originalBurstMana, " Mana");
            String text4 = getTextWithCheckingValue(I18n.get("botanicprobe.text.time_before_mana_loss"), currentTicksBeforeManaLoss, originalTicksBeforeManaLoss, " Ticks");
            String text5 = getTextWithCheckingValue(I18n.get("botanicprobe.text.mana_loss_speed"), currentManaLossPerTick, originalManaLossPerTick, " Mana / Tick");
            String text6 = getTextWithCheckingValue(I18n.get("botanicprobe.text.burst_speed"), currentMotionModifier, originalMotionModifier, I18n.get("botanicprobe.text.times"));

            ContentCollector.addText(TOPUtil.WAND_STACK, Arrays.asList(text1, text2, text3, text4, text5, text6));
        }
    }

    private String getTextWithCheckingValue(String label, int currentValue, int originalValue, String unit) {
        if (currentValue == originalValue) {
            return label + currentValue + unit;
        } else {
            return label + currentValue + unit
                    + " (" + I18n.get("botanicprobe.text.initially")
                    + TOPUtil.floatToPercentage((float) currentValue / (float) originalValue) + ")";
        }
    }

    private String getTextWithCheckingValue(String label, float currentValue, float originalValue, String unit) {
        if (currentValue == originalValue) {
            return label + currentValue + unit;
        } else {
            return label + currentValue + unit
                    + " (" + I18n.get("botanicprobe.text.initially")
                    + TOPUtil.floatToPercentage(currentValue / originalValue) + ")";
        }
    }
}