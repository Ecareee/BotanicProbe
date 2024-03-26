package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.BotanicProbe;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalApothecary;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WorkingTile implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "workingtile");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof de.melanx.botanicalmachinery.blocks.base.WorkingTile tile && !(level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalApothecary)) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.getManaCap();
            final int progress = tile.getProgress();
            final int maxProgress = tile.getMaxProgress();
            int light_blue = new Color(39, 255, 247).getRGB();
            int gray = java.awt.Color.gray.getRGB();
            int white = java.awt.Color.white.getRGB();

            if (manaCap != 0) {
                iProbeInfo.text("Mana: " + mana + "/" + manaCap);
            }
            if (progress != 0) {
                iProbeInfo.progress(progress, maxProgress, new ProgressStyle()
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
