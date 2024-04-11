package com.ecaree.botanicprobe.botania.common;

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

public class TileMod implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("tilemod");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof vazkii.botania.common.block.tile.TileMod && blockState.hasAnalogOutputSignal()) {
            final int analogOutputSignal = blockState.getAnalogOutputSignal(level, data.getPos());

            if (analogOutputSignal != 0) {
                ContentCollector.addText(new ItemStack(Items.REDSTONE),
                        I18n.get("botanicprobe.text.analog_signal_output") + analogOutputSignal);
            }
        }
    }
}