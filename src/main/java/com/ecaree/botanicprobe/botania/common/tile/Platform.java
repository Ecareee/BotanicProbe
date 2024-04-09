package com.ecaree.botanicprobe.botania.common.tile;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.TilePlatform;

public class Platform implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("platform");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TilePlatform tile) {
            BlockState state = tile.getCamoState();

            if (state != null) {
                Block block = state.getBlock();

                ContentCollector.addText(block,
                        I18n.get("botanicprobe.text.disguise") + new ItemStack(block).getDisplayName().getString());
            }
        }
    }
}