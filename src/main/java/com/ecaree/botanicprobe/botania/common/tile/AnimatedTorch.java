package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileAnimatedTorch;

public class AnimatedTorch implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("animatedtorch");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileAnimatedTorch tile) {
            final int side = tile.side;
            Direction direction = TileAnimatedTorch.SIDES[side].getOpposite();
            String directionName = TOPUtil.directionToString(direction);
            final int torchModeIndex = tile.getUpdateTag().getInt("torchMode");
            TileAnimatedTorch.TorchMode torchMode = TileAnimatedTorch.TorchMode.values()[torchModeIndex];
            String torchModeName = switch (torchMode) {
                case TOGGLE -> I18n.get("botania.animatedTorch.toggle");
                case ROTATE -> I18n.get("botania.animatedTorch.rotate");
                case RANDOM -> I18n.get("botania.animatedTorch.random");
            };

            if (torchMode == TileAnimatedTorch.TorchMode.RANDOM) {
                final int nextRandomRotation = tile.nextRandomRotation;
                Direction nextRandomRotationDirection = TileAnimatedTorch.SIDES[nextRandomRotation].getOpposite();
                String nextRandomRotationName = TOPUtil.directionToString(nextRandomRotationDirection);
                String nextRandomRotationAngle = " (+" + (nextRandomRotation - side + 4) % 4 * 90 + "°)";

                ContentCollector.addText(TOPUtil.COMPASS,
                        I18n.get("botanicprobe.text.direction") + directionName,
                        I18n.get("botanicprobe.text.next_random_rotation") + nextRandomRotationName + nextRandomRotationAngle);
            } else {
                ContentCollector.addText(TOPUtil.COMPASS,
                        I18n.get("botanicprobe.text.direction") + directionName);
            }

            ContentCollector.addText(TOPUtil.STATUS_STACK,
                    I18n.get("botanicprobe.text.mode") + torchModeName);
        }
    }
}