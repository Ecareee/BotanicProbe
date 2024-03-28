package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.subtile.generating.SubTileHydroangeas;

public class Hydroangeas implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("hydroangeas");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof SubTileHydroangeas tile) {
            final int passiveDecayTicks = tile.getUpdateTag().getInt(SubTileHydroangeas.TAG_PASSIVE_DECAY_TICKS);
            final int generationDelay = tile.getGenerationDelay();

            if (passiveDecayTicks != 0 && passiveDecayTicks != 1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.passive_decay_time") + (SubTileHydroangeas.DECAY_TIME - passiveDecayTicks) + " Ticks");
            } else if (passiveDecayTicks == 1) {
                iProbeInfo.text(I18n.get("botanicprobe.text.passive_decay_time") + (SubTileHydroangeas.DECAY_TIME - passiveDecayTicks) + " Tick");
            }
            iProbeInfo.text(I18n.get("botanicprobe.text.generation_delay") + generationDelay + " Ticks");
        }
    }
}
