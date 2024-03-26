package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.BotanicProbe;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileTerraPlate;
import vazkii.botania.common.block.tile.mana.TilePool;

public class TerraPlate implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "terraplate");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileTerraPlate tile) {
            final int mana = tile.getCurrentMana();
            final int targetMana = TilePool.MAX_MANA / 2;
            int light_blue = new Color(39, 255, 247).getRGB();
            int gray = java.awt.Color.gray.getRGB();
            int white = java.awt.Color.white.getRGB();

            if (mana != 0) {
                iProbeInfo.text("Mana: " + mana + "/" + targetMana);
                iProbeInfo.progress(mana, targetMana, new ProgressStyle()
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
