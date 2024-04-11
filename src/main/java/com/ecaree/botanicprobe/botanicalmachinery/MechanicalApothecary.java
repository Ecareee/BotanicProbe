package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalApothecary;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalApothecary implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("mechanicalapothecary");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalApothecary tile) {
            final int fluid = tile.getFluidInventory().getFluidAmount();
            final int fluidCap = BlockEntityMechanicalApothecary.FLUID_CAPACITY;
            final int progress = tile.getProgress();
            final int maxProgress = tile.getMaxProgress();

            ContentCollector.addText(new ItemStack(Items.WATER_BUCKET),
                    I18n.get("botanicprobe.text.fluid") + fluid + "/" + fluidCap + " mB");

            if (progress != 0) {
                ContentCollector.addProgressBar(progress, maxProgress);
            }
        }
    }
}