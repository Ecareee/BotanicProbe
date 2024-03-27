package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.BotanicProbe;
import com.ecaree.botanicprobe.TOPUtil;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalApothecary;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalApothecary implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "mechanicalapothecary");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalApothecary tile) {
            final int fluid = tile.getFluidInventory().getFluidAmount();
            final int fluidCap = BlockEntityMechanicalApothecary.FLUID_CAPACITY;
            final int progress = tile.getProgress();
            final int maxProgress = tile.getMaxProgress();

            iProbeInfo.text(I18n.get("botanicprobe.text.fluid") + fluid + "/" + fluidCap + " mB");
            if (progress != 0) {
                TOPUtil.setProgressBar(iProbeInfo, progress, maxProgress);
            }
        }
    }
}
