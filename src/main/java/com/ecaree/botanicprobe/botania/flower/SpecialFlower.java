package com.ecaree.botanicprobe.botania.flower;

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
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;

public class SpecialFlower implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("specialflower");
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileEntitySpecialFlower tile) {
            if (tile instanceof TileEntityGeneratingFlower generatingFlower) {
                // 提取方法有牛逼的 BUGS，别动
                final int mana = generatingFlower.getMana();
                final int manaMax = generatingFlower.getMaxMana();
                String text1;
                String text2 = null;
                String text3 = "Mana: " + mana + "/" + manaMax;

                if (generatingFlower.isValidBinding()) {
                    text1 = I18n.get("botanicprobe.text.binding")
                            + TOPUtil.posToString(generatingFlower.getBindingPos());
                } else {
                    text1 = I18n.get("botanicprobe.text.unbound");

                    if (generatingFlower.getBindingPos() != null) {
                        text2 = I18n.get("botanicprobe.text.last_binding")
                                + TOPUtil.posToString(generatingFlower.getBindingPos());
                    }
                }

                if (text2 == null) {
                    ContentCollector.addText(TOPUtil.WAND_STACK, text1, text3);
                } else {
                    ContentCollector.addText(TOPUtil.WAND_STACK, text1, text2, text3);
                }

            } else if (tile instanceof TileEntityFunctionalFlower functionalFlower) {
                final int mana = functionalFlower.getMana();
                final int manaMax = functionalFlower.getMaxMana();
                final int redstoneSignal = functionalFlower.redstoneSignal;
                String text1;
                String text2 = null;
                String text3 = "Mana: " + mana + "/" + manaMax;

                if (functionalFlower.isValidBinding()) {
                    text1 = I18n.get("botanicprobe.text.binding")
                            + TOPUtil.posToString(functionalFlower.getBindingPos());
                } else {
                    text1 = I18n.get("botanicprobe.text.unbound");

                    if (functionalFlower.getBindingPos() != null) {
                        text2 = I18n.get("botanicprobe.text.last_binding")
                                + TOPUtil.posToString(functionalFlower.getBindingPos());
                    }
                }

                if (text2 == null) {
                    ContentCollector.addText(TOPUtil.WAND_STACK, text1, text3);
                } else {
                    ContentCollector.addText(TOPUtil.WAND_STACK, text1, text2, text3);
                }

                if (functionalFlower.acceptsRedstone() && redstoneSignal != 0) {
                    ContentCollector.addText(new ItemStack(Items.REDSTONE),
                            I18n.get("botanicprobe.text.redstone_signal_accepted") + redstoneSignal);
                }
            }
        }
    }
}