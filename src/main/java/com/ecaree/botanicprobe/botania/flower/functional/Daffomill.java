package com.ecaree.botanicprobe.botania.flower.functional;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.functional.SubTileDaffomill;

public class Daffomill implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("daffomill");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileDaffomill tile) {
            final int orientation = tile.getUpdateTag().getInt("orientation");
            Direction direction = Direction.from3DDataValue(orientation);
            String directionName = switch (direction) {
                case NORTH -> I18n.get("botanicprobe.text.north");
                case SOUTH -> I18n.get("botanicprobe.text.south");
                case WEST -> I18n.get("botanicprobe.text.west");
                case EAST -> I18n.get("botanicprobe.text.east");
                default -> "null";
            };
            final boolean powered = tile.getUpdateTag().getBoolean("powered");
            String poweredName = powered ? I18n.get("botanicprobe.text.yes") : I18n.get("botanicprobe.text.no");

            iProbeInfo.text(I18n.get("botanicprobe.text.direction") + directionName);
            iProbeInfo.text(I18n.get("botanicprobe.text.powered") + poweredName);
        }
    }
}
