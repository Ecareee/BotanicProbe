package com.ecaree.botanicprobe.botania.common.tile.corporea;

import com.ecaree.botanicprobe.util.ContentCollector;
import com.ecaree.botanicprobe.util.TOPUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.common.block.tile.corporea.TileCorporeaFunnel;

import java.util.List;

public class CorporeaFunnel implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return TOPUtil.RL("corporeafunnel");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData data) {
        if (level.getBlockEntity(data.getPos()) instanceof TileCorporeaFunnel tile) {
            List<ItemStack> filter = tile.getFilter();

            if (!filter.isEmpty()) {
                ContentCollector.addText(filter, I18n.get("botanicprobe.text.filtered_items"), false, false);
            }
        }
    }
}