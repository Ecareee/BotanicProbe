package com.ecaree.botanicprobe.botania;

import com.ecaree.botanicprobe.BotanicProbe;
import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileRuneAltar;

public class RuneAltar implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "runealtar");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileRuneAltar tile) {
            final int mana = tile.getCurrentMana();
            final int targetMana = tile.getTargetMana();

            if (mana != 0 && targetMana != 0) {
                iProbeInfo.text("Mana: " + mana + "/" + targetMana);
                TOPUtil.setProgressBar(iProbeInfo, mana, targetMana);
            }
        }
    }
}
