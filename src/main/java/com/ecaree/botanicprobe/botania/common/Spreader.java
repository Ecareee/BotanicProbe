package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.mixin.AccessorTileSpreader;
import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
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

public class Spreader implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("spreader");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileSpreader tile) {
            final int mana = tile.getCurrentMana();
            final int manaMax = tile.getMaxMana();

            ContentCollector.addText("Mana: " + mana + "/" + manaMax);

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
                BurstProperties props = new BurstProperties(variant.burstMana, variant.preLossTicks, variant.lossPerTick, 0F, variant.motionModifier, variant.color);
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

            displayWithCheckingValue(I18n.get("botanicprobe.text.burst_mana"), currentBurstMana, originalBurstMana, " Mana");
            displayWithCheckingValue(I18n.get("botanicprobe.text.time_before_mana_loss"), currentTicksBeforeManaLoss, originalTicksBeforeManaLoss, " Ticks");
            displayWithCheckingValue(I18n.get("botanicprobe.text.mana_loss_speed"), currentManaLossPerTick, originalManaLossPerTick, " Mana / Tick");
            displayWithCheckingValue(I18n.get("botanicprobe.text.burst_speed"), currentMotionModifier, originalMotionModifier, I18n.get("botanicprobe.text.times"));
        }
    }

    private void displayWithCheckingValue(String label, int currentValue, int originalValue, String unit) {
        java.text.NumberFormat percentFormat = java.text.NumberFormat.getPercentInstance();
        if (currentValue == originalValue) {
            ContentCollector.addText(label + currentValue + unit);
        } else {
            ContentCollector.addText(label + currentValue + unit
                    + " (" + I18n.get("botanicprobe.text.initially")
                    + percentFormat.format((float) currentValue / originalValue) + ")");
        }
    }

    private void displayWithCheckingValue(String label, float currentValue, float originalValue, String unit) {
        java.text.NumberFormat percentFormat = java.text.NumberFormat.getPercentInstance();
        if (currentValue == originalValue) {
            ContentCollector.addText(label + currentValue + unit);
        } else {
            ContentCollector.addText(label + currentValue + unit
                    + " (" + I18n.get("botanicprobe.text.initially")
                    + percentFormat.format(currentValue / originalValue) + ")");
        }
    }
}