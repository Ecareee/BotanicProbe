package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalManaPool;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalManaPool implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("mechanicalmanapool");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalManaPool tile) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.getManaCap();

            ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: " + mana + "/" + manaCap);
        }
    }
}