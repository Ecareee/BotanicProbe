package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalDaisy;
import de.melanx.botanicalmachinery.config.LibXServerConfig;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalDaisy implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("mechanicaldaisy");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalDaisy tile) {
            final int[] workingTicks = tile.getUpdateTag().getIntArray("workingTicks");

            for (int i = 0; i < 8; i++) {
                if (workingTicks[i] == 0 || workingTicks[i] == -1) {
                    continue;
                }

                final int workingTicksMax = tile.getRecipe(tile.getState(i)).getTime() * LibXServerConfig.WorkingDurationMultiplier.mechanicalDaisy;

                ContentCollector.addProgressBar(workingTicks[i], workingTicksMax);
            }
        }
    }
}