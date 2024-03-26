package com.ecaree.botanicprobe.botanicalmachinery;

import com.ecaree.botanicprobe.BotanicProbe;
import de.melanx.botanicalmachinery.blocks.tiles.BlockEntityMechanicalManaPool;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MechanicalManaPool implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return new ResourceLocation(BotanicProbe.MOD_ID, "mechanicalmanapool");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof BlockEntityMechanicalManaPool tile) {
            final int mana = tile.getCurrentMana();
            final int manaCap = tile.getManaCap();

            iProbeInfo.text("Mana: " + mana + "/" + manaCap);
        }
    }
}
