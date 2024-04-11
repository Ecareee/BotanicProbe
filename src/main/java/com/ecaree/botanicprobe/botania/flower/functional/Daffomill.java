package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.functional.SubTileDaffomill;

public class Daffomill implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("daffomill");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileDaffomill tile) {
            final int orientation = tile.getUpdateTag().getInt("orientation");
            Direction direction = Direction.from3DDataValue(orientation);
            String directionName = TOPUtil.directionToString(direction);
            final boolean powered = tile.getUpdateTag().getBoolean("powered");
            String isPowered = TOPUtil.booleanToString(powered);

            ContentCollector.addText(TOPUtil.COMPASS,
                    I18n.get("botanicprobe.text.direction") + directionName);
            ContentCollector.addText(new ItemStack(Items.REDSTONE),
                    I18n.get("botanicprobe.text.powered") + isPowered);
        }
    }
}