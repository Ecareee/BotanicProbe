package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.BotanicProbe;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalDaisy;
import de.melanx.botanicalmachinery.config.LibXServerConfig;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalDaisy implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "mechanicaldaisy");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalDaisy tile) {
            final int[] workingTicks = tile.getUpdateTag().getIntArray("workingTicks");
            int light_blue = new Color(39, 255, 247).getRGB();
            int gray = java.awt.Color.gray.getRGB();
            int white = java.awt.Color.white.getRGB();

            for (int i = 0; i < 8; i++) {
                if (workingTicks[i] == 0 || workingTicks[i] == -1) {
                    continue;
                }
                final int workingTicksMax = tile.getRecipe(tile.getState(i)).getTime() * LibXServerConfig.WorkingDurationMultiplier.mechanicalDaisy;
                iProbeInfo.progress(workingTicks[i], workingTicksMax, new ProgressStyle()
                        .width(90)
                        .height(4)
                        .numberFormat(NumberFormat.NONE)
                        .borderColor(white)
                        .backgroundColor(gray)
                        .filledColor(light_blue)
                        .alternateFilledColor(light_blue));
            }
        }
    }
}