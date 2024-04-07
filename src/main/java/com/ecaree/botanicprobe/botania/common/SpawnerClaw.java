package com.ecaree.botanicprobe.botania.common;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TileSpawnerClaw;

public class SpawnerClaw implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("spawnerclaw");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileSpawnerClaw tile) {
            final int mana = tile.getCurrentMana();

            ContentCollector.addText(TOPUtil.MANA_STACK, "Mana: " + mana + "/" + 160); // 硬编码 160，TileSpawnerClaw.MAX_MANA = 160
        }
    }
}