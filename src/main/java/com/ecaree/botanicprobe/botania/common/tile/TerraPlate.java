package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileTerraPlate;

public class TerraPlate implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.rl("terraplate");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileTerraPlate tile) {
            final int mana = tile.getCurrentMana();
            final int targetMana = tile.getCurrentMana() + tile.getAvailableSpaceForMana();

            if (mana != 0) {
                ContentCollector.addTextWithProgressBar(TOPUtil.MANA_STACK,
                        "Mana: " + mana + "/" + targetMana,
                        mana, targetMana);
            }
        }
    }
}