package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.TOPUtil;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalApothecary;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WorkingTile implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("workingtile");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof de.melanx.botanicalmachinery.blocks.base.WorkingTile tile && !(level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalApothecary)) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.getManaCap();
            final int progress = tile.getProgress();
            final int maxProgress = tile.getMaxProgress();

            if (manaCap != 0) {
                iProbeInfo.text("Mana: " + mana + "/" + manaCap);
            }
            if (progress != 0) {
                TOPUtil.setProgressBar(iProbeInfo, progress, maxProgress);
            }
        }
    }
}
