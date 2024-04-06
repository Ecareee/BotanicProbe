package com.ecaree.botanicprobe.botania.flower.generating;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
                ContentCollector.addText(new ItemStack(Items.DEAD_BUSH),
                        I18n.get("botanicprobe.text.passive_decay_time") + (SubTileHydroangeas.DECAY_TIME - passiveDecayTicks) + " Ticks");
            } else if (passiveDecayTicks == 1) {
                ContentCollector.addText(new ItemStack(Items.DEAD_BUSH),
                        I18n.get("botanicprobe.text.passive_decay_time") + (SubTileHydroangeas.DECAY_TIME - passiveDecayTicks) + " Tick");
            }

            ContentCollector.addText(new ItemStack(Items.SNOWBALL),
                    I18n.get("botanicprobe.text.generation_delay") + generationDelay + " Ticks");
        }
    }
}