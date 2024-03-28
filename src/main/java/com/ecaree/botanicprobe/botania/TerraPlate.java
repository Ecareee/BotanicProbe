package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileTerraPlate;
import vazkii.botania.common.block.tile.mana.TilePool;

public class TerraPlate implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("terraplate");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileTerraPlate tile) {
            final int mana = tile.getCurrentMana();
            final int targetMana = TilePool.MAX_MANA / 2;

            if (mana != 0) {
                iProbeInfo.text("Mana: " + mana + "/" + targetMana);
                TOPUtil.setProgressBar(iProbeInfo, mana, targetMana);
            }
        }
    }
}
