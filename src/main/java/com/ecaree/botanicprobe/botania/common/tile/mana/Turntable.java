package com.ecaree.botanicprobe.botania.common.tile.mana;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.mana.TileTurntable;

public class Turntable implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("turntable");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileTurntable tile) {
            final int speed = tile.getUpdateTag().getInt("speed");
            final boolean backwards = tile.getUpdateTag().getBoolean("backwards");

            char motion = backwards ? '<' : '>';
            String speedStr = ChatFormatting.BOLD + "";
            for (int i = 0; i < speed; i++) {
                speedStr = speedStr + motion;
            }

            ContentCollector.addText(TOPUtil.STATUS_STACK, speedStr, 0x006600);
        }
    }
}